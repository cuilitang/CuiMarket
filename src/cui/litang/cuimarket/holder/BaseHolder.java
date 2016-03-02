package cui.litang.cuimarket.holder;

import android.view.View;

/**
 * 代码越简化越好吗？不应该为了少两行代码把业务逻辑写在构造里吧？第一次见这么奇葩的构造，逻辑方法应该是让子类一步一步去调用吧，那样便于理解，便于记忆
 * @author Cuilitang
 *
 * @param <T>
 */
public abstract class BaseHolder<T> {
	
    private View view;
	
	private T mData;

	/**
	 * 第一次见这么奇葩的构造
	 * 调用initView方法初始化View，setTag（）
	 */
	public BaseHolder() {
		view = initView();
		view.setTag(this);
	}
	
	public View getRootView(){
		return view;
	}

	/**
	 * 当convertView 为空时，需要初始化view
	 * @return
	 */
	public abstract View initView() ;

	/**
	 * 获得传过来的数据，并调用refreshView，刷新界面
	 * @param data
	 */
	public void setData(T data){
		this.mData = data;
		refreshView();
	}
	
	/**
	 * 刷新界面
	 */
	public abstract  void refreshView();

	public T getData(){
		return mData;
	}
}
