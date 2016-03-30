package cui.litang.cuimarket;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.holder.DetailDesHolder;
import cui.litang.cuimarket.holder.DetailInfoHolder;
import cui.litang.cuimarket.holder.DetailSafeHolder;
import cui.litang.cuimarket.holder.DetailScrollHolder;
import cui.litang.cuimarket.jsonparser.DetailProtocol;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.LoadingPage;
import cui.litang.cuimarket.widget.LoadingPage.LoadingState;

public class DetailActivity extends BaseActivity {

	private String packageName;
	private AppInfo appInfo;

	@Override
	protected void initView() {
		
		LoadingPage lp = new LoadingPage(UIUtils.getContext()){

			@Override
			public View createSuccessView() {
				return DetailActivity.this.createSuccessView();
			}

			@Override
			public LoadingState load() {
				return DetailActivity.this.load();
			}
			
		};
		
		setContentView(lp);
		lp.show();
	}

	protected LoadingState load() {
		
		DetailProtocol protocol = new DetailProtocol();
		protocol.setPackageName(packageName);
		appInfo = protocol.load(0);
		
		if(null == appInfo){
			return LoadingState.ERROR;
		}
		return LoadingState.SUCCESS;
	}

	protected View createSuccessView() {
		
		View view = UIUtils.inflate(R.layout.activity_detail);
		
		//第一部分
		FrameLayout detail_info = (FrameLayout) view.findViewById(R.id.detail_info);
		DetailInfoHolder holder = new DetailInfoHolder();
		holder.setData(appInfo);
		detail_info.addView(holder.getRootView());
		
		//第二部分
		FrameLayout detail_safe = (FrameLayout) view.findViewById(R.id.detail_safe);
		DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
		detailSafeHolder.setData(appInfo);
		detail_safe.addView(detailSafeHolder.getRootView());
		
		//第三部分
		HorizontalScrollView detail_scroll = (HorizontalScrollView) view.findViewById(R.id.detail_screen);
		DetailScrollHolder detailScrollHolder = new DetailScrollHolder();
		detailScrollHolder.setData(appInfo);
		detail_scroll.addView(detailScrollHolder.getRootView());
		
		//第四部分
		FrameLayout detail_des = (FrameLayout) view.findViewById(R.id.detail_des);
		DetailDesHolder detailDesHolder = new DetailDesHolder();
		detailDesHolder.setData(appInfo);
		detail_des.addView(detailDesHolder.getRootView());
		
		
		return view;
	}

	@Override
	protected void init() {
		
		Intent intent = getIntent();
		if(intent != null){
			 packageName = intent.getStringExtra("packageName");
			 
		}
	}

}
