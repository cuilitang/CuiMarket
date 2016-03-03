package cui.litang.cuimarket.widget;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import cui.litang.cuimarket.R;

/**
 * 设置了点击事件选择器的ListView
 * @author Cuilitang
 *
 */
public class BaseListView extends ListView {

	public BaseListView(Context context) {
		super(context);
		init();
	}
	
	public BaseListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	
	public BaseListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	/**
	 * 设置点击选择器
	 */
	private void init() {
		setSelector(R.drawable.nothing);
	}

	
	

}
