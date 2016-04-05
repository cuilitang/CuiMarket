package cui.litang.cuimarket.adapter;

import java.util.List;

import android.content.Intent;
import android.widget.ListView;
import cui.litang.cuimarket.DetailActivity;
import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.holder.BaseHolder;
import cui.litang.cuimarket.holder.HomeHolder;
import cui.litang.cuimarket.utils.UIUtils;

public abstract class BaseListAdapter extends MyBaseAdapter<AppInfo> {

	public BaseListAdapter(ListView view, List<AppInfo> mDatas) {
		super(view, mDatas);
	}

	@Override
	public BaseHolder<AppInfo> getHolder() {
		return new HomeHolder();
	}
	
	@Override
	public void OnInnerItemClick(int position) {
		Intent intent = new Intent(UIUtils.getContext(),DetailActivity.class);
		intent.putExtra("packageName", getmDatas().get(position).getPackageName());
		UIUtils.startActivity(intent);
	}


}
