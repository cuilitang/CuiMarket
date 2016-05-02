package cui.litang.cuimarket.holder;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cui.litang.cuimarket.R;
import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.utils.UIUtils;

public class DetailDesHolder extends BaseHolder<AppInfo> implements OnClickListener {

	private TextView des_content;
	private TextView des_author;
	private ImageView des_arrow;
	private AppInfo appInfo;
	private RelativeLayout des_layout;
	private boolean mIsInit = true;
	private ScrollView mScrollView;

	@Override
	public View initView() {
		
		View view = UIUtils.inflate(R.layout.app_detail_des);
		des_layout = (RelativeLayout) view.findViewById(R.id.des_layout);
		des_content = (TextView)view.findViewById(R.id.des_content); //简介内容
		des_author = (TextView)view.findViewById(R.id.des_author); //作者
		des_arrow = (ImageView)view.findViewById(R.id.des_arrow);//收放箭头
		des_arrow.setTag(false);
		des_layout.setOnClickListener(this);
		return view;
	}

	@Override
	public void refreshView() {
		
		appInfo = getData();
		des_content.setText(appInfo.getDes());
		des_author.setText("作者:"+appInfo.getAuthor());
		
		if(mIsInit){
			des_content.getLayoutParams().height = measureShortHeight();
			mIsInit = false;
		}
	}

	private int measureShortHeight() {

		int measuredWidth = des_content.getMeasuredWidth();
		int width = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY);
		int height = MeasureSpec.makeMeasureSpec(2000, MeasureSpec.AT_MOST);
		TextView textView = getTextView();
		textView.setLines(7);
		textView.setMaxLines(7);
		textView.measure(width, height);
		return textView.getMeasuredHeight();
	}

	private TextView getTextView() {
		TextView textView = new TextView(UIUtils.getContext());
		textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
		textView.setText(getData().getDes());
		return textView;
	}

	/**
	 * OnClickListener  
	 * @param v  展开收起箭头
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.des_layout:
			expend();
			break;
		default:
			break;
		}
		
	}

	private void expend() {
		
		final LayoutParams layoutParams = des_content.getLayoutParams();
		int shortHeight = measureShortHeight();
		int longHeight = measureLongHeight();
		ValueAnimator va;
		final boolean flag = (Boolean) des_arrow.getTag();
		if(flag){
			des_arrow.setTag(false);
			va = ValueAnimator.ofInt(longHeight,shortHeight);
		}else{
			des_arrow.setTag(true);
			va = ValueAnimator.ofInt(shortHeight,longHeight);
		}
		
		des_layout.setEnabled(false);  //动画进行中，停止时间反应
		va.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {

				layoutParams.height = (Integer) animation.getAnimatedValue();
				des_content.setLayoutParams(layoutParams);
				if(!flag){
					if(mScrollView == null){
						mScrollView = getSrollView();
					}else{
						mScrollView.scrollTo(0,mScrollView.getHeight());
					}
					
				}
				
			}

		} );
		
		va.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {}
				
			@Override
			public void onAnimationRepeat(Animator animation) {}
			
			@Override
			public void onAnimationEnd(Animator animation) {

				boolean flag = (Boolean) des_arrow.getTag();
				des_arrow.setImageResource(flag?R.drawable.arrow_up:R.drawable.arrow_down);
				des_layout.setEnabled(true);
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {}
			
		});
		
		va.setDuration(300);
		va.start();
		
		
	}

	private int measureLongHeight() {

		int measuredWidth = des_content.getMeasuredWidth();
		int width = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY);
		int height = MeasureSpec.makeMeasureSpec(2000, MeasureSpec.AT_MOST);
		TextView textView = getTextView();
		textView.measure(width, height);
		return textView.getMeasuredHeight();
	}
	
	private ScrollView getSrollView() {

		ScrollView scv = null;
		View currentView = des_layout;
		while(true){
			ViewParent parent = currentView.getParent();
			
			if(parent == null ||!(parent instanceof View)){
				break;
			}else if(parent instanceof ScrollView){
				scv = (ScrollView) parent;
				break;
			}else{
				currentView = (View) parent;
			}
		}
		return scv;
	}
}
