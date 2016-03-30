package cui.litang.cuimarket;

import java.util.List;

import com.bumptech.glide.Glide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import cui.litang.cuimarket.http.HttpHelper;
import cui.litang.cuimarket.utils.UIUtils;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageScaleActivity extends Activity {

	private ViewPager pager;
	private int position;
	private List<String> mDatas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.image_scale_activity);
		pager = (ViewPager)findViewById(R.id.pager);
		
		init();
	}

	private void init() {
		
		Intent intent = getIntent();
		if(null!=intent){
			mDatas = intent.getStringArrayListExtra("imageUrls");
			position = intent.getIntExtra("position",0);
			
		}
		ImageScaleAdapter imageScaleAdapter = new ImageScaleAdapter();
		pager.setAdapter(imageScaleAdapter);
		pager.setCurrentItem(position,false);
	}
	
	private class ImageScaleAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return mDatas.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0==arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			View view = View.inflate(UIUtils.getContext(), R.layout.item_image_scale, null);
			ImageView image = (ImageView) view.findViewById(R.id.image);
			Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + mDatas.get(position)).into(image);
			PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);
			photoViewAttacher.update();
			container.addView(image);
			
			return view;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
	}

}
