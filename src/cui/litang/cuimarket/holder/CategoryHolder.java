package cui.litang.cuimarket.holder;

import com.bumptech.glide.Glide;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cui.litang.cuimarket.R;
import cui.litang.cuimarket.bean.CategoryInfo;
import cui.litang.cuimarket.http.HttpHelper;
import cui.litang.cuimarket.utils.StringUtils;
import cui.litang.cuimarket.utils.UIUtils;

public class CategoryHolder extends BaseHolder<CategoryInfo> {

	private ImageView iv_1;
	private ImageView iv_2;
	private ImageView iv_3;
	private TextView tv_1;
	private TextView tv_2;
	private TextView tv_3;
	private RelativeLayout rl_1;
	private RelativeLayout rl_2;
	private RelativeLayout rl_3;
	
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.category_item);
		iv_1 = (ImageView) view.findViewById(R.id.iv_1);
		tv_1 = (TextView) view.findViewById(R.id.tv_1);
		rl_1 = (RelativeLayout) view.findViewById(R.id.rl_1);
		
		iv_2 = (ImageView) view.findViewById(R.id.iv_2);
		tv_2 = (TextView) view.findViewById(R.id.tv_2);
		rl_2 = (RelativeLayout) view.findViewById(R.id.rl_2);
		
		iv_3 = (ImageView) view.findViewById(R.id.iv_3);
		tv_3 = (TextView) view.findViewById(R.id.tv_3);
		rl_3 = (RelativeLayout) view.findViewById(R.id.rl_3);
		return view;
	}

	@Override
	public void refreshView() {

		CategoryInfo categoryInfo = getData();
		String imageUrl1 = categoryInfo.getImageUrl1();
		String imageUrl2 = categoryInfo.getImageUrl2();
		String imageUrl3 = categoryInfo.getImageUrl3();
		
		String name1 = categoryInfo.getName1();
		String name2 = categoryInfo.getName2();
		String name3 = categoryInfo.getName3();
		
		if(StringUtils.isEmpty(imageUrl1)){
			iv_1.setImageDrawable(null);
			tv_1.setText("");
			rl_1.setEnabled(false);
		}else{
			Glide.with(UIUtils.getContext()).load(HttpHelper.URL+"image?name="+imageUrl1).into(iv_1);
			tv_1.setText(name1);
			rl_1.setEnabled(true);
		}
		
		if(StringUtils.isEmpty(imageUrl2)){
			iv_2.setImageDrawable(null);
			tv_2.setText("");
			rl_2.setEnabled(false);
		}else{
			Glide.with(UIUtils.getContext()).load(HttpHelper.URL+"image?name="+imageUrl2).into(iv_2);
			tv_2.setText(name2);
			rl_2.setEnabled(true);
		}
		
		if(StringUtils.isEmpty(imageUrl3)){
			iv_3.setImageDrawable(null);
			tv_3.setText("");
			rl_3.setEnabled(false);
		}else{
			Glide.with(UIUtils.getContext()).load(HttpHelper.URL+"image?name="+imageUrl3).into(iv_3);
			tv_3.setText(name3);
			rl_3.setEnabled(true);
		}

	}

}
