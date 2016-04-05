package cui.litang.cuimarket.fragment;

import java.util.List;
import android.view.View;
import android.widget.ListView;
import cui.litang.cuimarket.BaseFragment;
import cui.litang.cuimarket.adapter.BaseListAdapter;
import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.jsonparser.AppProtocol;
import cui.litang.cuimarket.jsonparser.HomeProtocol;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.BaseListView;
import cui.litang.cuimarket.widget.LoadingPage.LoadingState;

public class AppFragment extends BaseFragment {

	private List<AppInfo> mDatas;

	@Override
	protected View createSuccessView() {
		BaseListView listView = new BaseListView(UIUtils.getContext());
		AppAdapter appAdapter = new AppAdapter(listView, mDatas);
		listView.setAdapter(appAdapter);
		return listView;
	}

	@Override
	protected LoadingState load() {

		AppProtocol protocol = new AppProtocol();
		mDatas = protocol.load(0);
		return checkJson(mDatas);
	}
	
	private class AppAdapter extends BaseListAdapter{

		public AppAdapter(ListView view, List<AppInfo> mDatas) {
			super(view, mDatas);
			view.setOnItemClickListener(this);
		}

		@Override
		protected List onLoadMore() {
			AppProtocol protocol = new AppProtocol();
			return protocol.load(mDatas.size());
		}
	}
	
	


}
