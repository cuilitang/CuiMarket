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

public class HomeHolder extends BaseHolder<AppInfo> {

	private ImageView item_icon;
	private TextView item_title;
	private RatingBar item_rating;
	private TextView item_size;
	private TextView item_bottom;
	
	@Override
	public View initView() {
		
		View view = UIUtils.inflate(R.layout.list_item);
		item_icon = (ImageView) view.findViewById(R.id.item_icon);
		item_title = (TextView) view.findViewById(R.id.item_title);
		item_rating = (RatingBar) view.findViewById(R.id.item_rating);
		item_size = (TextView) view.findViewById(R.id.item_size);
		item_bottom = (TextView) view.findViewById(R.id.item_bottom);
		
		return view;
	}

	@Override
	public void refreshView() {
		
		AppInfo appInfo = getData();
		item_title.setText(appInfo.getName());
		item_rating.setRating(appInfo.getStars());
		item_size.setText(StringUtils.formatFileSize(appInfo.getSize()));
		item_bottom.setText(appInfo.getDes());
		Glide.with(UIUtils.getContext()).load(HttpHelper.URL+"image?name="+appInfo.getIconUrl()).into(item_icon);
	}

}
