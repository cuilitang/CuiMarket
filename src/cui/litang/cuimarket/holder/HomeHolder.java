package cui.litang.cuimarket.holder;
import com.bumptech.glide.Glide;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cui.litang.cuimarket.R;
import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.bean.DownloadInfo;
import cui.litang.cuimarket.http.HttpHelper;
import cui.litang.cuimarket.manager.DownloadManager;
import cui.litang.cuimarket.utils.StringUtils;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.ProgressArc;

public class HomeHolder extends BaseHolder<AppInfo> implements OnClickListener {

	private ImageView item_icon;
	private TextView item_title;
	private RatingBar item_rating;
	private TextView item_size;
	private TextView item_bottom;
	
	private FrameLayout action_progress;
	private TextView action_txt;
	private RelativeLayout item_action;
	
	
	private int mState;
	private DownloadManager mDownloadManager;
	private DownloadInfo downloadInfo;
	private float mProgress;
	private ProgressArc mProgressArc;
	
	@Override
	public View initView() {
		
		View view = UIUtils.inflate(R.layout.list_item);
		item_icon = (ImageView) view.findViewById(R.id.item_icon);
		item_title = (TextView) view.findViewById(R.id.item_title);
		item_rating = (RatingBar) view.findViewById(R.id.item_rating);
		item_size = (TextView) view.findViewById(R.id.item_size);
		item_bottom = (TextView) view.findViewById(R.id.item_bottom);
		
		action_progress = (FrameLayout) view.findViewById(R.id.action_progress);
		action_txt = (TextView) view.findViewById(R.id.action_txt);
		item_action = (RelativeLayout) view.findViewById(R.id.item_action);
		
		item_action.setOnClickListener(this);
		// 设置下载的背景图片
		item_action.setBackgroundResource(R.drawable.list_item_action_bg);

		mProgressArc = new ProgressArc(UIUtils.getContext());
		int arcDiameter = UIUtils.dip2px(26);
		// 设置圆的直径
		mProgressArc.setArcDiameter(arcDiameter);
		// 设置进度条的颜色
		mProgressArc.setProgressColor(UIUtils.getColor(R.color.progress));
		int size = UIUtils.dip2px(27);
		// 进度条的宽高
		action_progress.addView(mProgressArc, new ViewGroup.LayoutParams(size,
				size));
		
		return view;
	}

	@Override
	public void setData(AppInfo data) {

		if (mDownloadManager == null) {
			mDownloadManager = DownloadManager.getInstance();
		}
		downloadInfo = mDownloadManager.getDownInfo(data.getId());
		if (downloadInfo != null) {
			mState = downloadInfo.getDownloadState();
			mProgress = downloadInfo.getProgress();
		} else {
			mState = DownloadManager.STATE_NONE;
			mProgress = 0;
		}
		super.setData(data);
	}
	
	@Override
	public void refreshView() {
		
		AppInfo appInfo = getData();
		item_title.setText(appInfo.getName());
		item_rating.setRating(appInfo.getStars());
		item_size.setText(StringUtils.formatFileSize(appInfo.getSize()));
		item_bottom.setText(appInfo.getDes());
		Glide.with(UIUtils.getContext()).load(HttpHelper.URL+"image?name="+appInfo.getIconUrl()).into(item_icon);
		
		refreshState(mState, mProgress);
	}
	
	/**
	 * 刷新状态
	 * 
	 * @param mState
	 * @param mProgress
	 */
	public void refreshState(int state, float progress) {
		mState = state;
		mProgress = progress;
		switch (mState) {
		case DownloadManager.STATE_NONE:
			mProgressArc.seForegroundResource(R.drawable.ic_download);
			//是否画进度条，不画进度条
			mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
			action_txt.setText("下载");
			break;
		case DownloadManager.STATE_PAUSED:
			mProgressArc.seForegroundResource(R.drawable.ic_resume);
			mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
			action_txt.setText("暂停");
			break;
		case DownloadManager.STATE_ERROR:
			mProgressArc.seForegroundResource(R.drawable.ic_redownload);
			mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
			action_txt.setText("失败");
			break;
		case DownloadManager.STATE_WAITING:
			mProgressArc.seForegroundResource(R.drawable.ic_pause);
			//是否画进度条
			mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
			mProgressArc.setProgress(progress, false);
			action_txt.setText("等待");
			break;
		case DownloadManager.STATE_DLOWNLOADING:
			mProgressArc.seForegroundResource(R.drawable.ic_pause);
			//画进度条
			mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
			mProgressArc.setProgress(progress, true);
			action_txt.setText((int) (mProgress * 100) + "%");
			break;
		case DownloadManager.STATE_DOWNLOADED:
			mProgressArc.seForegroundResource(R.drawable.ic_install);
			mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
			action_txt.setText("安装");
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.item_action:
			// 如果当前是默认的或者是暂停的或者是错误的。才需要下载
			if (DownloadManager.STATE_NONE == mState
					|| DownloadManager.STATE_PAUSED == mState
					|| DownloadManager.STATE_ERROR == mState) {
				mDownloadManager.download(getData());
			} else if (DownloadManager.STATE_DLOWNLOADING == mState
					|| DownloadManager.STATE_WAITING == mState) {
				mDownloadManager.stop(getData());
			} else if (DownloadManager.STATE_DOWNLOADED == mState) {
				mDownloadManager.install(getData());
			}
			break;

		}

	}
}
