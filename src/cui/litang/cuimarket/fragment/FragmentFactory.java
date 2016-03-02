package cui.litang.cuimarket.fragment;

import java.util.HashMap;

import cui.litang.cuimarket.BaseFragment;

public class FragmentFactory {
	


	private static final int TAB_HOME = 0;
	private static final int TAB_APP = 1;
	private static final int TAB_GAME = 2;
	private static final int TAB_SUBJECT = 3;
	private static final int TAB_RECOMMENT = 4;
	private static final int TAB_CATEGORY = 5;
	private static final int TAB_HOT = 6;

	private static HashMap<Integer, BaseFragment> mFragments = new HashMap<Integer, BaseFragment>();
	private static BaseFragment fragment;

	public static BaseFragment createFragment(int position) {
		// 获取到内存里面是否有fragment
		fragment = mFragments.get(position);
		// 说明内存没有
		if (null == fragment) {
			switch (position) {
			case TAB_HOME:
				fragment = new HomeFragment();
				break;

			case TAB_APP:
				fragment = new AppFragment();
				break;
			case TAB_GAME:
				fragment = new GameFragment();
				break;
			case TAB_SUBJECT:
				fragment = new SubjectFragment();
				break;
			case TAB_RECOMMENT:
				fragment = new RecommentFragment();
				break;
			case TAB_CATEGORY:
				fragment = new CategoryFragment();
				break;
			case TAB_HOT:
				fragment = new HotFragment();
				break;
			}
			mFragments.put(position, fragment);

		}

		return fragment;
	}

	

}
