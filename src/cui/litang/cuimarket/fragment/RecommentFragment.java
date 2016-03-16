package cui.litang.cuimarket.fragment;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import cui.litang.cuimarket.BaseFragment;
import cui.litang.cuimarket.jsonparser.RecommendProtocol;
import cui.litang.cuimarket.randomLayout.StellarMap;
import cui.litang.cuimarket.randomLayout.StellarMap.Adapter;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.LoadingPage.LoadingState;

public class RecommentFragment extends BaseFragment {

	private List<String> mDatas;
	@Override
	protected View createSuccessView() {
		
		StellarMap stellarMap = new StellarMap(UIUtils.getContext());
		stellarMap.setRegularity(6, 9);
		int padding = UIUtils.dip2px(13);
		stellarMap.setInnerPadding(padding, padding, padding, padding);
		stellarMap.setAdapter(new StellarMapAdapter());
		stellarMap.setGroup(0, true);
		
		return stellarMap;
	}

	@Override
	protected LoadingState load() {
		RecommendProtocol protocol = new RecommendProtocol();
		mDatas = protocol.load(0);
		return checkJson(mDatas);
	}
	
	private class StellarMapAdapter implements Adapter{
		
		private Random mRandom;
		
		public StellarMapAdapter() {
			mRandom = new Random();
		}

		//几组数据
		@Override
		public int getGroupCount() {
			return 2;
		}

		//每组数据条数
		@Override
		public int getCount(int group) {
			return 15;
		}

		
		@Override
		public View getView(int group, int position, View convertView) {
			TextView textView = new TextView(UIUtils.getContext());
			
			int red = 20 + mRandom.nextInt(220);
			int green = 20 + mRandom.nextInt(220);
			int black = 20 + mRandom.nextInt(220);
			int color = Color.rgb(red, green, black);
			textView.setTextColor(color);
			textView.setTextSize(10+mRandom.nextInt(15));
			textView.setText(mDatas.get(position));
			return textView;
		}

		@Override
		public int getNextGroupOnPan(int group, float degree) {
			return (group+1)%2;
		}

		@Override
		public int getNextGroupOnZoom(int group, boolean isZoomIn) {
			return (group+1)%2;
		}
		
	}
	
}
