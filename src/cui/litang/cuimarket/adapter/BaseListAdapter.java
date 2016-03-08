package cui.litang.cuimarket.adapter;

import java.util.List;

import android.widget.ListView;
import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.holder.BaseHolder;
import cui.litang.cuimarket.holder.HomeHolder;

public abstract class BaseListAdapter extends MyBaseAdapter<AppInfo> {

	public BaseListAdapter(ListView view, List<AppInfo> mDatas) {
		super(view, mDatas);
	}

	@Override
	public BaseHolder<AppInfo> getHolder() {
		return new HomeHolder();
	}

}
