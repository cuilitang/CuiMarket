package cui.litang.cuimarket.fragment;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cui.litang.cuimarket.BaseFragment;
import cui.litang.cuimarket.adapter.MyBaseAdapter;
import cui.litang.cuimarket.fragment.AppFragment.ViewHolder;
import cui.litang.cuimarket.holder.BaseHolder;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.LoadingPage.LoadingState;

public class HomeFragment extends BaseFragment {
	


	private List<String> mDatas;

	@Override
	protected View createSuccessView() {
		ListView view = new ListView(UIUtils.getContext());
		HomeAdapter adapter = new HomeAdapter(view,mDatas);
		view.setAdapter(adapter);
		return view;
	}

	@Override
	protected LoadingState load() {

		mDatas = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			mDatas.add("这是一条Home数据"+i);
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		return checkJson(mDatas);
	}
	
	private class HomeAdapter extends MyBaseAdapter{


		public HomeAdapter(ListView view, List<String> mDatas) {
			super(view, mDatas);
		}

		@Override
		public BaseHolder getHolder() {
		
			return new ViewHolder();
		}
		
	}
	
	static class ViewHolder extends BaseHolder<String>{
		
		TextView textView;

		@Override
		public View initView() {
			textView = new TextView(UIUtils.getContext());
			return textView;
		}

		@Override
		public void refreshView() {
			textView.setText(getData());

		}

	}


}
