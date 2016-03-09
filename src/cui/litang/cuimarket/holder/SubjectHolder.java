package cui.litang.cuimarket.holder;

import com.bumptech.glide.Glide;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cui.litang.cuimarket.R;
import cui.litang.cuimarket.bean.SubjectInfo;
import cui.litang.cuimarket.http.HttpHelper;
import cui.litang.cuimarket.utils.UIUtils;

public class SubjectHolder extends BaseHolder<SubjectInfo> {

	private ImageView item_icon;
	private TextView item_txt;
	
	@Override
	public View initView() {
		
		View view = UIUtils.inflate(R.layout.subject_item);
		item_icon = (ImageView) view.findViewById(R.id.item_icon);
		item_txt = (TextView) view.findViewById(R.id.item_txt);
		return view;
	}

	@Override
	public void refreshView() {
		SubjectInfo data = getData();
		item_txt.setText(data.getDes());
		String url = HttpHelper.URL + "image?name=" + data.getUrl();
		Glide.with(UIUtils.getContext()).load(url).into(item_icon);
	}
}
