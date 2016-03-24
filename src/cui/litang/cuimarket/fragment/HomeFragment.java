package cui.litang.cuimarket.fragment;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cui.litang.cuimarket.BaseFragment;
import cui.litang.cuimarket.DetailActivity;
import cui.litang.cuimarket.adapter.BaseListAdapter;
import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.jsonparser.HomeProtocol;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.BaseListView;
import cui.litang.cuimarket.widget.LoadingPage.LoadingState;

public class HomeFragment extends BaseFragment{

	private List<AppInfo> mDatas;

	@Override
	protected View createSuccessView() {

		BaseListView listView = new BaseListView(UIUtils.getContext());
		HomeAdapter homeAdapter = new HomeAdapter(listView, mDatas);
		listView.setAdapter(homeAdapter);
		
		return listView;
	}

	@Override
	protected LoadingState load() {
		
		HomeProtocol protocol = new HomeProtocol();
		mDatas = protocol.load(0);
		return checkJson(mDatas);
	}
	
	private class HomeAdapter extends BaseListAdapter implements OnItemClickListener {

		public HomeAdapter(ListView view, List<AppInfo> mDatas) {
			super(view, mDatas);
			view.setOnItemClickListener(this);
		}

		@Override
		protected List onLoadMore() {
			HomeProtocol protocol = new HomeProtocol();
			return protocol.load(getmDatas().size());
		}

		/**
		 * OnItemClickListener
		 */
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
			Intent intent = new Intent(UIUtils.getContext(),DetailActivity.class);
			intent.putExtra("packageName", getmDatas().get(position).getPackageName());
			startActivity(intent);
		}
		
		

		
	}

	
	
	


}
