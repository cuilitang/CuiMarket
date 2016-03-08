package cui.litang.cuimarket.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import cui.litang.cuimarket.R;
import cui.litang.cuimarket.adapter.MyBaseAdapter;
import cui.litang.cuimarket.utils.UIUtils;

public class MoreHolder extends BaseHolder<Integer> implements OnClickListener {
	
	//表示有更多的数据
		public static int HAS_MORE = 1;
		//表示没有更多的数据
		public static int NO_MORE = 2;
		//表示跟服务器交互失败
		public static int ERROR = 3;
		private RelativeLayout rl_more_loading;
		private RelativeLayout rl_more_error;
		private MyBaseAdapter adapter;

	public MoreHolder(boolean hasMore, MyBaseAdapter myBaseAdapter) {
		
		setData(hasMore?HAS_MORE:NO_MORE);
		this.adapter = myBaseAdapter;
	}

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_more_loading);
		rl_more_loading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);
		rl_more_error = (RelativeLayout) view.findViewById(R.id.rl_more_error);
		rl_more_error.setOnClickListener(this);
		return view;
	}

	@Override
	public void refreshView() {

		Integer data = getData();
		rl_more_loading.setVisibility(data == HAS_MORE?View.VISIBLE:View.GONE);
		rl_more_error.setVisibility(data == ERROR?View.VISIBLE:View.GONE);
	}

	
	@Override
	public View getRootView() {

		if(getData()==HAS_MORE){
			loadMore();
		}
		return super.getRootView();
	}
	
	private void loadMore() {

		adapter.loadMore();
	}

	/**
	 * 当加载更多失败时候，点击条目重新加载
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		loadMore();
	}

}
