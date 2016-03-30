package cui.litang.cuimarket.holder;

import java.util.ArrayList;

import com.bumptech.glide.Glide;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import cui.litang.cuimarket.ImageScaleActivity;
import cui.litang.cuimarket.R;
import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.http.HttpHelper;
import cui.litang.cuimarket.utils.UIUtils;

public class DetailScrollHolder extends BaseHolder<AppInfo> implements OnClickListener{

	private ImageView[] screens;
	
	@Override
	public View initView() {
		
		View view = UIUtils.inflate(R.layout.app_detail_screen);
		screens = new ImageView[5];
		
		screens[0] = (ImageView) view.findViewById(R.id.screen_1);
		screens[1] = (ImageView) view.findViewById(R.id.screen_2);
		screens[2] = (ImageView) view.findViewById(R.id.screen_3);
		screens[3] = (ImageView) view.findViewById(R.id.screen_4);
		screens[4] = (ImageView) view.findViewById(R.id.screen_5);
		
		screens[0].setOnClickListener(this);
		screens[1].setOnClickListener(this);
		screens[2].setOnClickListener(this);
		screens[3].setOnClickListener(this);
		screens[4].setOnClickListener(this);
		
		
		
		return view;
	}

	@Override
	public void refreshView() {
		AppInfo appInfo = getData();
		for (int i = 0; i < 5; i++) {
			if(i<appInfo.getScreen().size()){
				screens[i].setVisibility(View.VISIBLE);
				Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + appInfo.getScreen().get(i)).into(screens[i]);
			}else{
				screens[i].setVisibility(View.GONE);
			}
		}
		
	}

	/**
	 * OnClickListener
	 */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.screen_1:
			enterImageScaleActivity(0);
			break;
		case R.id.screen_2:
			enterImageScaleActivity(1);
			break;
		case R.id.screen_3:
			enterImageScaleActivity(2);
			break;
		case R.id.screen_4:
			enterImageScaleActivity(3);
			break;
		case R.id.screen_5:
			enterImageScaleActivity(4);
			break;
		default:
			break;
		}
		
	}

	private void enterImageScaleActivity(int position) {
		
		Intent intent = new Intent(UIUtils.getContext(),ImageScaleActivity.class);
		intent.putExtra("position", position);
		
		intent.putStringArrayListExtra("imageUrls", (ArrayList<String>)getData().getScreen());
		UIUtils.startActivity(intent);
	}

}
