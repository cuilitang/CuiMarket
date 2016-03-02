package cui.litang.cuimarket.fragment;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cui.litang.cuimarket.BaseFragment;
import cui.litang.cuimarket.fragment.HomeFragment.ViewHolder;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.LoadingPage.LoadingState;

public class AppFragment extends BaseFragment {
	

	


	private List<String> mDatas;

	@Override
	protected View createSuccessView() {
		ListView view = new ListView(UIUtils.getContext());
		HomeAdapter adapter = new HomeAdapter();
		view.setAdapter(adapter);
		return view;
	}

	@Override
	protected LoadingState load() {

		mDatas = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			mDatas.add("这是一条应用数据"+i);
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		return checkJson(mDatas);
	}
	
	private class HomeAdapter extends BaseAdapter{

		private ViewHolder holder;

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

		/**
		 * convertView :The old view to reuse, if possible.
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			
			if(convertView == null){
				holder = new ViewHolder();
				convertView = new TextView(UIUtils.getContext());
				holder.textView = (TextView) convertView;
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.textView.setText(mDatas.get(position));
			
			return convertView;
		}
		
	}
	
	static class ViewHolder{
		
		TextView textView;
	}



}
