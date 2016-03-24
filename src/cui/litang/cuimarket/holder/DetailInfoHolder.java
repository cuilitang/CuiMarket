package cui.litang.cuimarket.holder;

import com.bumptech.glide.Glide;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import cui.litang.cuimarket.R;
import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.http.HttpHelper;
import cui.litang.cuimarket.utils.StringUtils;
import cui.litang.cuimarket.utils.UIUtils;

public class DetailInfoHolder extends BaseHolder<AppInfo> {

	private ImageView item_icon;
	private TextView item_title;
	private RatingBar item_rating;
	private TextView item_download;
	private TextView item_version;
	private TextView item_date;
	private TextView item_size;
	
	@Override
	public View initView() {
		
		View view = UIUtils.inflate(R.layout.app_detail_info);
		item_icon = (ImageView) view.findViewById(R.id.item_icon);
		item_title = (TextView) view.findViewById(R.id.item_title);
		item_rating = (RatingBar) view.findViewById(R.id.item_rating);
		item_download = (TextView) view.findViewById(R.id.item_download);
		item_version = (TextView) view.findViewById(R.id.item_version);
		item_date = (TextView) view.findViewById(R.id.item_date);
		item_size = (TextView) view.findViewById(R.id.item_size);
		return view;
	}

	@Override
	public void refreshView() {
		
		AppInfo data = getData();
		item_title.setText(data.getName());
		item_rating.setRating(data.getStars());
		item_download.setText("下载:" + data.getDownloadNum());
		item_version.setText("版本:" + data.getVersion());
		item_date.setText("时间:" + data.getDate());
		item_size.setText("大小:" + StringUtils.formatFileSize(data.getSize()));
		Glide.with(UIUtils.getContext()).load(HttpHelper.URL+"image?name=" + data.getIconUrl()).into(item_icon);
	}

}
