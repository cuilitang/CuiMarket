package cui.litang.cuimarket.fragment;

import java.util.List;

import android.view.View;
import android.widget.ListView;
import cui.litang.cuimarket.BaseFragment;
import cui.litang.cuimarket.adapter.BaseListAdapter;
import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.jsonparser.GameProtocol;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.BaseListView;
import cui.litang.cuimarket.widget.LoadingPage.LoadingState;

public class GameFragment extends BaseFragment {

	private List<AppInfo> mDatas;

	
	@Override
	protected View createSuccessView() {
		BaseListView listView = new BaseListView(UIUtils.getContext());
		GameAdapter homeAdapter = new GameAdapter(listView, mDatas);
		listView.setAdapter(homeAdapter);
		return listView;
	}

	@Override
	protected LoadingState load() {
		GameProtocol protocol = new GameProtocol();
		mDatas = protocol.load(0);
		return checkJson(mDatas);
	}
	
	
	private class GameAdapter extends BaseListAdapter{

		public GameAdapter(ListView view, List<AppInfo> mDatas) {
			super(view, mDatas);
		}

		@Override
		protected List onLoadMore() {
			// TODO Auto-generated method stub
			return null;
		}
	}

}
