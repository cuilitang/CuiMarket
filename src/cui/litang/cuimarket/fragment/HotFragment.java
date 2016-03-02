package cui.litang.cuimarket.fragment;

import android.view.View;
import android.widget.TextView;
import cui.litang.cuimarket.BaseFragment;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.LoadingPage.LoadingState;

public class HotFragment extends BaseFragment {

	@Override
	protected View createSuccessView() {
		// TODO Auto-generated method stub
		TextView tv = new TextView(UIUtils.getContext());
		tv.setText("hotttttttttttttt");
		return tv;
	}

	@Override
	protected LoadingState load() {
		// TODO Auto-generated method stub
		return LoadingState.EMPTY;
	}

}
