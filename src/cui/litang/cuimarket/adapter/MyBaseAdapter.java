package cui.litang.cuimarket.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import cui.litang.cuimarket.Holder.BaseHolder;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

	public ListView mListView;
	private List<T> mDatas;
	private BaseHolder<T> holder;
	
	public MyBaseAdapter() {
		super();
		
	}

	public MyBaseAdapter(ListView view, List<T> mDatas) {
		mListView = view;
		this.mDatas = mDatas;
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
		return holder.getRootView();
	}

	/**
	 * 每个类的Holder是不一样的，所以需要子类来重写
	 * @return
	 */
	public abstract BaseHolder<T> getHolder();

}
