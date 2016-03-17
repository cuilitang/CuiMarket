package cui.litang.cuimarket.fragment;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import cui.litang.cuimarket.BaseFragment;
import cui.litang.cuimarket.jsonparser.HotProtocol;
import cui.litang.cuimarket.utils.DrawableUtils;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.FlowLayout;
import cui.litang.cuimarket.widget.LoadingPage.LoadingState;

public class HotFragment extends BaseFragment {

	private List<String> mDatas;
	@Override
	protected View createSuccessView() {
		ScrollView scrollView = new ScrollView(UIUtils.getContext());
		FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
		
		int mVerticalSpacing = UIUtils.dip2px(4);
		int mHorizontalSpacing = UIUtils.dip2px(7);
		int padding = UIUtils.dip2px(13);
		
		flowLayout.setPadding(padding, padding, padding, padding);
		flowLayout.setVerticalSpacing(mVerticalSpacing);
		flowLayout.setHorizontalSpacing(mHorizontalSpacing);
		
		Random random = new Random();
		for (int i = 0; i < mDatas.size(); i++) {
			int red = 20 + random.nextInt(220);
			int green = 20 + random.nextInt(220);
			int blue = 20 + random.nextInt(220);
			int color = Color.rgb(red, green, blue);
			GradientDrawable drawable = DrawableUtils.createDrawable(color, color, 5);
			
			TextView textView = new TextView(UIUtils.getContext());
			textView.setGravity(Gravity.CENTER);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
			textView.setPadding(padding, padding, padding, padding);
			textView.setTextColor(Color.WHITE);
			textView.setBackgroundDrawable(drawable);
			textView.setText(mDatas.get(i));
			flowLayout.addView(textView);
		}
		
		scrollView.addView(flowLayout);
		return scrollView;
	}

	@Override
	protected LoadingState load() {

		HotProtocol protocol = new HotProtocol();
		mDatas = protocol.load(0);
		return checkJson(mDatas);
	}

}
