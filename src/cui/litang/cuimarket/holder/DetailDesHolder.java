package cui.litang.cuimarket.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cui.litang.cuimarket.R;
import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.utils.UIUtils;

public class DetailDesHolder extends BaseHolder<AppInfo> implements OnClickListener {

	private TextView des_content;
	private TextView des_author;
	private ImageView des_arrow;
	private AppInfo appInfo;
	private RelativeLayout des_layout;

	@Override
	public View initView() {
		
		View view = UIUtils.inflate(R.layout.app_detail_des);
		des_layout = (RelativeLayout) view.findViewById(R.id.des_layout);
		des_content = (TextView)view.findViewById(R.id.des_content); //简介内容
		des_author = (TextView)view.findViewById(R.id.des_author); //作者
		des_arrow = (ImageView)view.findViewById(R.id.des_arrow);//收放箭头
		
		des_layout.setOnClickListener(this);
		return view;
	}

	@Override
	public void refreshView() {
		
		appInfo = getData();
		des_content.setText(appInfo.getDes());
		des_author.setText("作者:"+appInfo.getAuthor());
		
		des_content.setMaxLines(5); //默认收起
		des_arrow.setTag(true);
	}

	/**
	 * OnClickListener  
	 * @param v  展开收起箭头
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.des_layout:
			expend();
			break;
		default:
			break;
		}
		
	}

	private void expend() {
		boolean flag = (Boolean) des_arrow.getTag();
		
		if(flag){
			des_arrow.setTag(false);
			des_content.setMaxLines(100); //打开
			des_arrow.setImageResource(R.drawable.arrow_up);
		}else{
			des_arrow.setTag(true);
			des_content.setMaxLines(5); //关闭
			des_arrow.setImageResource(R.drawable.arrow_down);
		}
	}
}
