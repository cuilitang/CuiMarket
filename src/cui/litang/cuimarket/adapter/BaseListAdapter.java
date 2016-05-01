package cui.litang.cuimarket.adapter;

import java.util.List;

import android.content.Intent;
import android.widget.ListView;
import cui.litang.cuimarket.DetailActivity;
import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.bean.DownloadInfo;
import cui.litang.cuimarket.holder.BaseHolder;
import cui.litang.cuimarket.holder.HomeHolder;
import cui.litang.cuimarket.manager.DownloadManager;
import cui.litang.cuimarket.manager.DownloadManager.DownloadObserver;
import cui.litang.cuimarket.utils.UIUtils;

public abstract class BaseListAdapter extends MyBaseAdapter<AppInfo> implements DownloadObserver  {

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

	
	public void startObserver() {
		DownloadManager.getInstance().registerDownloadObserver(this);
	}

	public void stopObserver() {
		DownloadManager.getInstance().unRegisterDownloadObserver(this);
	}

	@Override
	public void onDownloadStateChanged(final DownloadInfo info) {
		refreshHolder(info);
	}

	@Override
	public void onDownloadProgressChanged(DownloadInfo info) {
		refreshHolder(info);
	}
	
	private void refreshHolder(final DownloadInfo info) {
		// 获取到所有界面可见的holder
		List<BaseHolder> displayHolderLists = getDisplayHolderLists();

		if (displayHolderLists != null) {

			for (final BaseHolder holder : displayHolderLists) {

				final AppInfo appInfo = (AppInfo) holder.getData();

				UIUtils.runInMainThread(new Runnable() {

					@Override
					public void run() {
						// 说明当前是我需要刷新的holder
						if (appInfo.getId() == info.getId()) {
							((HomeHolder) holder).refreshState(
									info.getDownloadState(), info.getProgress());
						}

					}
				});

			}

		}

	}
	
	@Override
	protected List onLoadMore() {
		// TODO Auto-generated method stub
		return null;
	}

}
