package cui.litang.cuimarket.fragment;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import cui.litang.cuimarket.BaseFragment;
import cui.litang.cuimarket.adapter.MyBaseAdapter;
import cui.litang.cuimarket.bean.CategoryInfo;
import cui.litang.cuimarket.holder.BaseHolder;
import cui.litang.cuimarket.holder.CategoryHolder;
import cui.litang.cuimarket.holder.CategoryTitleHolder;
import cui.litang.cuimarket.jsonparser.CategoryProtocol;
import cui.litang.cuimarket.utils.UIUtils;
import cui.litang.cuimarket.widget.BaseListView;
import cui.litang.cuimarket.widget.LoadingPage.LoadingState;

public class CategoryFragment extends BaseFragment {
	
	private List<CategoryInfo> mDatas;

	@Override
	protected View createSuccessView() {
		
		BaseListView baseListView = new BaseListView(UIUtils.getContext());
		CategoryAdapter adapter = new CategoryAdapter(baseListView,mDatas);
		baseListView.setAdapter(adapter);
		return baseListView;
	}

	@Override
	protected LoadingState load() {

		CategoryProtocol protocol = new CategoryProtocol();
		mDatas = protocol.load(0);
		return checkJson(mDatas);
	}
	
	class CategoryAdapter extends MyBaseAdapter<CategoryInfo>{
		
		int position = 0;
		
		public CategoryAdapter(BaseListView baseListView, List<CategoryInfo> mDatas) {
			super(baseListView, mDatas);
		}
		
		
		@Override
		public int getViewTypeCount() {
			return super.getViewTypeCount()+1;
		}
		
		@Override
		public int getInnerItemViewType(int position) {
			if(mDatas.get(position).isTitle()){
				return 2;
			}else{
				return 1;  //普通
			}
			
		}
		

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			this.position = position;
			
			
			return super.getView(position, convertView, parent);
		}
		
		
		@Override
		public BaseHolder<CategoryInfo> getHolder() {
			if(mDatas.get(position).isTitle()){
				return new CategoryTitleHolder();
			}else{
				return new CategoryHolder();
			}
		}

		
		@Override
		protected List onLoadMore() {
			return null;
		}
		
		@Override
		public boolean hasMore() {
			return false;
		}
		
	}

}
