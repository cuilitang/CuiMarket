package cui.litang.cuimarket;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import cui.litang.cuimarket.fragment.FragmentFactory;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.PagerTab;

public class MainActivity extends BaseActivity implements OnPageChangeListener{
	
	private ViewPager mPager;
	private PagerTab mTabs;



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

}
