package cui.litang.cuimarket.fragment;

import java.util.List;

import android.view.View;
import android.widget.ListView;
import cui.litang.cuimarket.BaseFragment;
import cui.litang.cuimarket.adapter.BaseListAdapter;
import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.holder.HomePicHolder;
import cui.litang.cuimarket.jsonparser.HomeProtocol;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.BaseListView;
import cui.litang.cuimarket.widget.LoadingPage.LoadingState;

public class HomeFragment extends BaseFragment{

	private List<AppInfo> mDatas;
	private List<String> pictureUrl;
	private HomeAdapter mAdapter;
	
	/** 可见时，需要启动监听，以便随时根据下载状态刷新页面 */
	@Override
	public void onResume() {
		super.onResume();
		if (mAdapter != null) {
			mAdapter.startObserver();
			mAdapter.notifyDataSetChanged();
		}
	}
	 

	/** 不可见时，需要关闭监听 */
	@Override
	public void onPause() {
		super.onPause();
		if (mAdapter != null) {
			mAdapter.stopObserver();
		}
	}

	@Override
	protected View createSuccessView() {

		BaseListView listView = new BaseListView(UIUtils.getContext());
		
		//轮播图Holder
		if(null!=pictureUrl&&pictureUrl.size()>0){
			HomePicHolder homePicHolder = new HomePicHolder();
			homePicHolder.setData(pictureUrl);
			listView.addHeaderView(homePicHolder.getRootView());
		}
		
		mAdapter = new HomeAdapter(listView, mDatas);
		listView.setAdapter(mAdapter);
		
		mAdapter.startObserver();
		mAdapter.notifyDataSetChanged();
		
		return listView;
	}

	@Override
	protected LoadingState load() {
		
		HomeProtocol protocol = new HomeProtocol();
		mDatas = protocol.load(0);
		
		pictureUrl = protocol.getPictureUrl();//获取轮播图的地址Urlstring
		return checkJson(mDatas);
	}
	
	private class HomeAdapter extends BaseListAdapter {

		public HomeAdapter(ListView view, List<AppInfo> mDatas) {
			super(view, mDatas);
			view.setOnItemClickListener(this);
		}

		@Override
		protected List onLoadMore() {
			HomeProtocol protocol = new HomeProtocol();
			return protocol.load(getmDatas().size());
		}
	}
}
