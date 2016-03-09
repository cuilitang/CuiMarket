package cui.litang.cuimarket.fragment;

import java.util.List;

import android.view.View;
import android.widget.ListView;
import cui.litang.cuimarket.BaseFragment;
import cui.litang.cuimarket.adapter.MyBaseAdapter;
import cui.litang.cuimarket.bean.SubjectInfo;
import cui.litang.cuimarket.holder.BaseHolder;
import cui.litang.cuimarket.holder.SubjectHolder;
import cui.litang.cuimarket.jsonparser.SubjectProtocol;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.BaseListView;
import cui.litang.cuimarket.widget.LoadingPage.LoadingState;

public class SubjectFragment extends BaseFragment {

	private List<SubjectInfo> list;


	@Override
	protected View createSuccessView() {
		BaseListView listView = new BaseListView(UIUtils.getContext());
		SubjectAdapter adapter = new SubjectAdapter(listView, list);
		listView.setAdapter(adapter);
		return listView;
	}

	@Override
	protected LoadingState load() {
		SubjectProtocol protocol = new SubjectProtocol();
		list = protocol.load(0);
		return checkJson(list);
	}
	
	private class SubjectAdapter extends MyBaseAdapter<SubjectInfo>{
		
		public SubjectAdapter(ListView view, List<SubjectInfo> mDatas) {
			super(view, mDatas);
		}

		@Override
		public BaseHolder<SubjectInfo> getHolder() {
			return new 	SubjectHolder();
		}

		@Override
		protected List<SubjectInfo> onLoadMore() {
			SubjectProtocol protocol = new SubjectProtocol();
			return protocol.load(list.size());
		}
	}	
}
