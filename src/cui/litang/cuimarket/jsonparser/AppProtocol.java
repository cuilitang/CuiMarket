package cui.litang.cuimarket.jsonparser;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cui.litang.cuimarket.bean.AppInfo;
import cui.litang.cuimarket.utils.LogUtils;

public class AppProtocol extends BaseParser<List<AppInfo>> {

	@Override
	protected String getKey() {
		return "app";
	}

	@Override
	protected List<AppInfo> parseFromJson(String json) {
		try {
			List<AppInfo> list = new ArrayList<AppInfo>();
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				AppInfo info = new AppInfo();
				info.setId(obj.getLong("id"));
				info.setName(obj.getString("name"));
				info.setPackageName(obj.getString("packageName"));
				info.setIconUrl(obj.getString("iconUrl"));
				info.setStars(Float.valueOf(obj.getString("stars")));
				info.setSize(obj.getLong("size"));
				info.setDownloadUrl(obj.getString("downloadUrl"));
				info.setDes(obj.getString("des"));
				list.add(info);
			}
			return list;
		} catch (Exception e) {
			LogUtils.e(e);
			return null;
		}
	}
}
