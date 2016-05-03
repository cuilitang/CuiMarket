package cui.litang.cuimarket;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseActivity extends ActionBarActivity{
	
private static BaseActivity mForegroundActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		init();
		initActionBar();
	}
	
	protected void initActionBar() {}

	protected abstract void init();

	protected abstract void initView() ;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.mForegroundActivity = this;
	}
	
	
	public static BaseActivity getForegroundActivity(){
		return mForegroundActivity;
	}
	
}
