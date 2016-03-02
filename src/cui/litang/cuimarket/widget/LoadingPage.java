package cui.litang.cuimarket.widget;

import cui.litang.cuimarket.R;
import cui.litang.cuimarket.manager.ThreadManager;
import cui.litang.cuimarket.utils.UIUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
/**
 * 页面内容
 * 状态： 默认、加载中、加载成功、加载失败、空，需要根据不同状态显示不同界面
 * 入口为show()
 * @author Cuilitang
 *
 */
public abstract class LoadingPage extends FrameLayout {

	//默认状态
	private final int UN_LOADING = 1;
	//加载中状态
	private final int LOADING = 2;
	//加载失败状态
	private final int ERROR = 3;
	//加载成功，服务器无数据状态
	private final int EMPTY = 4;
	//加载成功状态
	private final int SUCESS = 5;
	
	private int mState;
	private View mLoadingView;
	private View mErrorView;
	private View mEmptyView;
	private View mSuccessView;
	
	
	public LoadingPage(Context context) {
		super(context);
		init();
	}
	public LoadingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public LoadingPage(Context context,AttributeSet attrs,int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {

		mState = UN_LOADING;
		mLoadingView = createLoadingView();
		mErrorView = createErrorView();
		mEmptyView = createEmptyView();
		
		if(null!= mLoadingView){
			addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		if(null!= mErrorView){
			addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		
		if(null!= mEmptyView){
			addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		
		showSafePage();
	}

	private void showSafePage() {
		
		UIUtils.runInMainThread(new Runnable() {
			
			@Override
			public void run() {
				showPage();
			}
		});
	}

	private void showPage() {
		
		if(null!=mLoadingView){
			
			mLoadingView.setVisibility(mState == UN_LOADING||mState == LOADING?View.VISIBLE:View.INVISIBLE);
		}
		
		if(null!=mErrorView){
			mErrorView.setVisibility(mState == ERROR?View.VISIBLE:View.INVISIBLE);
		}
		
		if(null!=mEmptyView){
			mEmptyView.setVisibility(mState == EMPTY?View.VISIBLE:View.INVISIBLE);
		}
		
		if(null==mSuccessView&&mState == SUCESS){
			mSuccessView = createSuccessView();
			addView(mSuccessView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		if(null!=mSuccessView){
			mSuccessView.setVisibility(mState==SUCESS?View.VISIBLE:View.INVISIBLE); 
		}
	
	}

	/**
	 * 下载数据成功界面，每个界面都不一样，需要子类来实现
	 * @return
	 */
	public abstract View createSuccessView();

	private View createEmptyView() {
		return UIUtils.inflate(R.layout.loading_page_empty);
	}
	
	private View createErrorView() {
		return UIUtils.inflate(R.layout.loading_page_error);
	}
	
	private View createLoadingView() {
		return UIUtils.inflate(R.layout.loading_page_loading);
	}
	
	/**
	 * 返回结果
	 * @author Cuilitang
	 *
	 */
	public enum LoadingState{
		ERROR(3),EMPTY(4),SUCCESS(5);
		int value;
		
		LoadingState(int value){
			this.value = value;
		}
		
		public int getValue(){
			return value;
		}
	}
	
	/**
	 * 加载数据
	 * @author Cuilitang
	 *
	 */
	private class LoadTask implements Runnable{

		@Override
		public void run() {
			final LoadingState result = load();
			mState = result.getValue();
			showSafePage();
		}
	}
	
	/**
	 * 加载数据方法，因为每个页面的数据都不一样，所以需要子类来实现
	 * @return
	 */
	public abstract LoadingState load();
	
	
	/**
	 * 显示界面方法，由BaseFragment调用
	 * 
	 */
	public void show(){
		//非正常情况下置为正常
		if(mState == ERROR || mState == EMPTY){
			mState = UN_LOADING;
		}
		//正常情况下开始加载
		if(mState == UN_LOADING){
			mState=LOADING;
			LoadTask task = new LoadTask();
			ThreadManager.getLongPool().execute(task);
		}
		showSafePage();
	}
	
}
