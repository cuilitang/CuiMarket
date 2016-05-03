package cui.litang.cuimarket.jsonparser;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import cui.litang.cuimarket.bean.UserInfo;
import cui.litang.cuimarket.utils.LogUtils;

public class UserProtocol extends BaseParser<List<UserInfo>> {

	@Override
	protected String getKey() {
		return "user";
	}

	@Override
	protected List<UserInfo> parseFromJson(String json) {
		try {
			List<UserInfo> list = new ArrayList<UserInfo>();
			JSONObject obj = new JSONObject(json);
			UserInfo info = new UserInfo();
			info.setName(obj.getString("name"));
			info.setEmail(obj.getString("email"));
			info.setUrl(obj.getString("url"));
			list.add(info);
			return list;
		} catch (Exception e) {
			LogUtils.e(e);
			return null;
		}
	}
}
