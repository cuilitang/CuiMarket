package cui.litang.cuimarket.holder;

import java.util.List;

import com.bumptech.glide.Glide;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import cui.litang.cuimarket.R;
import cui.litang.cuimarket.http.HttpHelper;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.IndicatorView;

public class HomePicHolder extends BaseHolder<List<String>> implements OnPageChangeListener {
	
	private List<String> mPics;
	private ViewPager mViewPager;
	private AutoPlayTask task;
	private IndicatorView indicatorView;

	@Override
	public View initView() {
		
		RelativeLayout relativeLayout = new RelativeLayout(UIUtils.getContext());
		LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,UIUtils.getDimens(R.dimen.list_header_hight));//初始RelativeLayout的宽和高
		relativeLayout.setLayoutParams(params);
		
		mViewPager = new ViewPager(UIUtils.getContext()); 
		RelativeLayout.LayoutParams picParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);// 初始ViewPager的宽和高
		mViewPager.setLayoutParams(picParams);
		
		HomePicAdapter homePicAdapter = new HomePicAdapter();
		mViewPager.setAdapter(homePicAdapter);
		
		indicatorView = new IndicatorView(UIUtils.getContext());  //右下角轮播黑点
		android.widget.RelativeLayout.LayoutParams pointParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		pointParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		pointParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		indicatorView.setLayoutParams(pointParams);
		indicatorView.setIndicatorDrawable(UIUtils.getDrawable(R.drawable.indicator));
		indicatorView.setInterval(5);
		indicatorView.setPadding(0, 0, 20, 20);
		indicatorView.setSelection(0);
		
		task = new AutoPlayTask(); //自动播放设置
		
		mViewPager.setOnTouchListener(new OnTouchListener() {  //手指按下时暂停轮播图
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if(event.getAction()==MotionEvent.ACTION_DOWN){
					task.stop();
				}else if(event.getAction() ==MotionEvent.ACTION_UP){
					task.start();
				}
				return false;
			}
		});
		
		mViewPager.setOnPageChangeListener(this);
		
		relativeLayout.addView(mViewPager);
		relativeLayout.addView(indicatorView);
		return relativeLayout;
	}

	@Override
	public void refreshView() {

		mPics = getData();
		mViewPager.setCurrentItem(mPics.size()*100);
		indicatorView.setCount(mPics.size());
		task.start();
	}
	
	
	private class HomePicAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			ImageView imageView = new ImageView(UIUtils.getContext());
			imageView.setScaleType(ScaleType.FIT_XY);
			
			int location = position%mPics.size();
			Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + mPics.get(location)).into(imageView);
			container.addView(imageView);
			return imageView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View) object);
		}
		
	}


	private class AutoPlayTask implements Runnable{
		
		// 每隔5秒钟跳动一次
		final int AUTO_PLAY_TIME = 5000;

		boolean isAutoPlay = false;
		
		@Override
		public void run() {
			if(isAutoPlay){
				mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1,false);
				UIUtils.postDelayed(this, AUTO_PLAY_TIME);
			}
		}

		public void stop() {
			if(isAutoPlay){
				isAutoPlay = false;
				UIUtils.removeCallbacks(this);
			}
		}

		public void start() {
			if (!isAutoPlay) {
				isAutoPlay =true;
				UIUtils.removeCallbacks(this);
				UIUtils.postDelayed(this, AUTO_PLAY_TIME);
			}
		}
	}
	
	
	
		
		
	/**
	 * OnPageChangeListener
	 */
	@Override
	public void onPageSelected(int position) {
		indicatorView.setSelection(position % mPics.size());
	}
		
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {}
		
	@Override
	public void onPageScrollStateChanged(int position) {
		
	}

}


