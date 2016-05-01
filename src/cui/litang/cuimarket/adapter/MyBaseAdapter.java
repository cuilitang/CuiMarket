package cui.litang.cuimarket.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.RecyclerListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import cui.litang.cuimarket.holder.BaseHolder;
import cui.litang.cuimarket.holder.MoreHolder;
import cui.litang.cuimarket.manager.ThreadManager;
import cui.litang.cuimarket.utils.UIUtils;

public abstract class MyBaseAdapter<T> extends BaseAdapter implements RecyclerListener,OnItemClickListener {

	
	public ListView mListView;
	private List<T> mDatas;
	private BaseHolder holder;
	private List<BaseHolder> mDisplayHolderList;//优化listView时使用
	
	private static final int MORE_ITEM_TYPE = 0;//加载更多时使用
	private static final int ITEM_DATA_TYPE = 1;//加载更多时使用
	
	private MoreHolder moreHolder;//加载更多时使用
	
	private boolean isLoading = false;//加载更多时使用
	
	public MyBaseAdapter() {
		super();
		
	}
	
	public List<BaseHolder> getDisplayHolderLists(){
		return mDisplayHolderList;
	}

	public MyBaseAdapter(ListView view, List<T> mDatas) {
		mListView = view;
		this.mDatas = mDatas;
		mDisplayHolderList = new ArrayList<BaseHolder>();
		if(null!= view){
			view.setRecyclerListener(this);
		}
	}

	

	public List<T> getmDatas() {
		return mDatas;
	}

	public void setmDatas(List<T> mDatas) {
		this.mDatas = mDatas;
	}
	
	
	
	

	@Override
	public int getCount() {
		return mDatas.size()+1; //添加一个“加载更多”条目
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	

	/**
	 * Get the type of View that will be created by getView for the specified item.
	 */
	@Override
	public int getItemViewType(int position) {
		//判断当前是否是最后一个条目
		if(position == getCount()-1){
			return MORE_ITEM_TYPE;
		}else{
			return getInnerItemViewType(position);
		}
	}
	
	public int getInnerItemViewType(int position) {
		// TODO Auto-generated method stub
		return ITEM_DATA_TYPE;
	}

	/**
	 * Returns the number of types of Views that will be created by getView. Each type represents a set of views that can be converted in getView. If the adapter always returns the same type of View for all items, this method should return 1. 
	 */
	@Override
	public int getViewTypeCount() {
		return super.getViewTypeCount()+1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			if(getItemViewType(position)==MORE_ITEM_TYPE){
				holder = getMoreHolder();
			}else{
				holder = getHolder();
			}
		}else{
			holder = (BaseHolder<T>) convertView.getTag();
		}
		
		if(getItemViewType(position)== ITEM_DATA_TYPE){
			holder.setData(mDatas.get(position));
		}
		if(getItemViewType(position)== 2){
			holder.setData(mDatas.get(position));
		}
		
		mDisplayHolderList.add(holder);  //这是优化内存时候用的，讲holder放进集合里，当view不在视野中之后直接将holder从集合中remove掉。
		return holder.getRootView();
	}

	/**
	 * 得到“加载更多”条目的holder
	 * @return
	 */
	private MoreHolder getMoreHolder() {
		if(moreHolder == null){
			moreHolder = new MoreHolder(hasMore(),this);
		}
		return moreHolder;
	}
	
	  /**
     * 表示有更多的数据
     * @return
     */
	public boolean hasMore() {
		return true;
	}

	/**
	 * 每个类的Holder是不一样的，所以需要子类来重写
	 * @return
	 */
	public abstract BaseHolder<T> getHolder();

	
	/**
	 * RecyclerListener  监听view销毁的
	 * 当销毁view时同时将holder销毁掉
	 */
	@Override
	public void onMovedToScrapHeap(View view) {
		System.out.println("View被回收了");
		if(null!=view){
			BaseHolder holder = (BaseHolder) view.getTag();
			if(null!=holder){
				synchronized (mDisplayHolderList) {
					mDisplayHolderList.remove(holder);
					System.out.println("Holder被回收了");
				} 
			}
		}
		
	}

	/**
	 * 当Holder状态为还有更多数据时候加载更多数据，并重新判断状态
	 */
	public void loadMore() {
		
		if(!isLoading){
			isLoading = true ;
			ThreadManager.getLongPool().execute(new Runnable() {
				
				@Override
				public void run() {
					final List list = onLoadMore();
					UIUtils.runInMainThread(new Runnable() {
						
						@Override
						public void run() {
							if(null==list){
								getMoreHolder().setData(MoreHolder.ERROR);
							}else if(list.size() < 20){
								getMoreHolder().setData(MoreHolder.NO_MORE);
							}else{
								getMoreHolder().setData(MoreHolder.HAS_MORE);
							}
							
							if(null!= list){
								if(null!=mDatas){
									mDatas.addAll(list);
								}else{
									setmDatas(list);
								}
							}
							
							//刷新界面
							notifyDataSetChanged();
							isLoading = false;
						}
					});
					
					
				}
			});
		}
		
	}

	
	/**
	 * 子方法实现，从服务器拉取更多数据
	 * @return
	 */
	protected abstract List onLoadMore();
	
	/**
	 * OnItemClickListener
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		position = position - getListViewHeadCount(position);
		OnInnerItemClick(position);
		
	}

	/**
	 * OnItemClickListener 应由子类来实现
	 * @param position 去掉ListView的Header之后得到的实际的position
	 */
	public void OnInnerItemClick(int position) {}

	private int getListViewHeadCount(int position) {
		
			int count = 0;
			if(null!=mListView){
					count = mListView.getHeaderViewsCount();
			}
		return count;
	}

}
