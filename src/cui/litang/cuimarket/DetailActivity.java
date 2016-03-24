package cui.litang.cuimarket;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.holder.DetailInfoHolder;
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
		FrameLayout detail_info = (FrameLayout) view.findViewById(R.id.detail_info);
		DetailInfoHolder holder = new DetailInfoHolder();
		holder.setData(appInfo);
		detail_info.addView(holder.getRootView());
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
