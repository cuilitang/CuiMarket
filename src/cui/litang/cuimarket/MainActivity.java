package cui.litang.cuimarket;

import android.annotation.SuppressLint;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import cui.litang.cuimarket.fragment.FragmentFactory;
import cui.litang.cuimarket.holder.MenuHolder;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.PagerTab;

public class MainActivity extends BaseActivity implements OnPageChangeListener{
	
	private DrawerLayout mDrawerLayout;
	//菜单
	private FrameLayout mDrawer;
	private MenuHolder mMenuHolder;
	//ActionBar
	private ActionBar mActionBar;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private ViewPager mPager;
	private PagerTab mTabs;



	
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		// 如果运行的环境 (部署到什么版本的手机 )大于3.0
		if (android.os.Build.VERSION.SDK_INT > 11) {
			SearchView searchView = (SearchView) menu.findItem(
					R.id.action_search).getActionView();
			searchView.setOnQueryTextListener(new OnQueryTextListener() {
				
				// 当搜索提交的时候
				@Override
				public boolean onQueryTextSubmit(String query) {
					Toast.makeText(getApplicationContext(), query, 0).show();
					return true;
				}
				// 当搜索的文本发生变化
				@Override
				public boolean onQueryTextChange(String newText) {
					return true;
				}
			});// 搜索的监听
		} 
		return true;
	}
	
	/** 菜单键点击的事件处理 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//在4.0以前，android通过菜单键来增加选项，在4.0后，提倡actionBar，所以菜单键增加的按钮可以显示到actionBar上，这里也能处理ActionBar上的菜单键事件
		 switch (item.getItemId()) {
	        case R.id.action_search:
	            Toast.makeText(this, "搜索按钮", 0).show();
	            return true;
	        default:
	            return mDrawerToggle.onOptionsItemSelected(item)|super.onOptionsItemSelected(item);
	    }
		
	}

	private class MainAdapter extends FragmentStatePagerAdapter{

		private String[] tab_names;
		private BaseFragment fragment;
		
		public MainAdapter(FragmentManager fm) {
			super(fm);
			tab_names = UIUtils.getStringArray(R.array.tab_names);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tab_names[position];
		}
		
		@Override
		public Fragment getItem(int postion) {
			return FragmentFactory.createFragment(postion);
		}

		@Override
		public int getCount() {
			return tab_names.length;
		}
		
	}
	/**
	 * OnPageChangeListener
	 */
	@Override
	public void initView() {
		
		setContentView(R.layout.activity_main);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerShadow(R.drawable.ic_drawer_shadow, GravityCompat.START);//设置阴影
		//菜单
		mDrawer = (FrameLayout) findViewById(R.id.start_drawer);
		mMenuHolder = new MenuHolder();
		mDrawer.addView(mMenuHolder.getRootView());
		
		//ViewPager、tab的初始化、tab和ViewPager的互相绑定
		mTabs = (PagerTab) findViewById(R.id.tabs);
		mPager = (ViewPager) findViewById(R.id.pager);
		MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
		mPager.setAdapter(adapter);
		mTabs.setViewPager(mPager);
		mTabs.setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {}
	

	@Override
	public void onPageSelected(int postion) {
		
		BaseFragment fragment = FragmentFactory.createFragment(postion);
		fragment.show();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

	
	/** actionBar的初始化 */
	protected void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		/*
		 *	1）显示Navigation Drawer的 Activity 对象
			2） DrawerLayout 对象
			3）一个用来指示Navigation Drawer的 drawable资源
			4）一个用来描述打开Navigation Drawer的文本 (用于支持可访问性)。
			5）一个用来描述关闭Navigation Drawer的文本(用于支持可访问性). 
		 */
		mDrawerToggle = new ActionBarDrawerToggle(this,
				mDrawerLayout, R.drawable.ic_drawer_am, R.string.drawer_open,
				R.string.drawer_close){

					@Override
					public void onDrawerClosed(View drawerView) {
						super.onDrawerClosed(drawerView);
						Toast.makeText(getApplicationContext(), "抽屉关闭了", 0).show();
					}
					@Override
					public void onDrawerOpened(View drawerView) {
						super.onDrawerOpened(drawerView);
						Toast.makeText(getApplicationContext(), "抽屉打开了", 0).show();
					}
			
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		//  让开关和actionbar建立关系 
		mDrawerToggle.syncState();
		
	}
	
	
}
