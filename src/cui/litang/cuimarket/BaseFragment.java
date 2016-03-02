package cui.litang.cuimarket;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.utils.ViewUtils;
import cui.litang.cuimarket.widget.LoadingPage;
import cui.litang.cuimarket.widget.LoadingPage.LoadingState;


public abstract class BaseFragment extends Fragment {
	

	//为什么加上static与去掉static竟然有这样大的区别？
	private LoadingPage mContentPage;
	
	
	
    //setContentView
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//当前的view已经有了一个父亲。必须移除
		if(mContentPage == null){
			mContentPage = new LoadingPage(UIUtils.getContext()) {

				@Override
				public View createSuccessView() {
					return BaseFragment.this.createSuccessView();
				}

				@Override
				public LoadingState load() {
					return BaseFragment.this.load();
				}
			};
		}else{
			ViewUtils.removeSelfFromParent(mContentPage);
		}
		
		return mContentPage;
	}
    /**
     * 检查服务器返回的json数据
     * @param object
     * @return
     */
	protected LoadingState checkJson(Object object) {
		if (object == null) {
			return LoadingState.ERROR;
		}
		if (object instanceof List) {
			List list = (List) object;
			if (list.size() == 0) {
				return LoadingState.EMPTY;
			}
		}

		return LoadingState.SUCCESS;
	}

	protected abstract LoadingState load();

	protected abstract View createSuccessView();

	public void show() {
		if (null != mContentPage) {
			mContentPage.show();
		}

	}

}
