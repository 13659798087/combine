package lzgene.newscreening.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Util {

	/**
	 系统左菜单栏
	 */
	public static List<Map<String, Object>> DataToTree(List<Map<String, Object>> data,
													   String parentKey, String parentValue, String primaryKey,
													   String propertyName) {
		List<Map<String, Object>> dic = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : data) {
			if (map.get(parentKey) != null
					&& map.get(parentKey).equals(parentValue)) {
				rows.add(map);
			} else if (map.get(parentKey) == parentValue) {
				rows.add(map);
			}
		}

		for (Map<String, Object> map : rows) {
			Map<String, Object> dicc = new HashMap<String, Object>();

			for (Entry<String, Object> entry : map.entrySet()) {
				dicc.put(entry.getKey(), entry.getValue());

			}

			dicc.put(propertyName,DataToTree(data, parentKey, map.get(primaryKey).toString(),primaryKey, propertyName));

			dic.add(dicc);
		}
		if (dic.size() == 0)
			return null;

		return dic;
	}



}