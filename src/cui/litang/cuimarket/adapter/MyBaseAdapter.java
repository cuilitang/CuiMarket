package cui.litang.cuimarket.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.RecyclerListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import cui.litang.cuimarket.holder.BaseHolder;

public abstract class MyBaseAdapter<T> extends BaseAdapter implements RecyclerListener {

	public ListView mListView;
	private List<T> mDatas;
	private BaseHolder<T> holder;
	private List<BaseHolder> mDisplayHolderList;
	
	public MyBaseAdapter() {
		super();
		
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
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			holder = getHolder();
		}else{
			holder = (BaseHolder<T>) convertView.getTag();
		}
		
		holder.setData(mDatas.get(position));
		mDisplayHolderList.add(holder);
		return holder.getRootView();
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

}
