package com.sooncode.jdbc4json.service;

import java.util.Hashtable;

import com.sooncode.jdbc4json.constant.DATA;

public class JdbcServiceFactory {
	private static Hashtable<String, JdbcService> services = new Hashtable<>();
	
	private JdbcServiceFactory() {//不容许创建对象
	}

	public static JdbcService getJdbcService(String dbKey) {

		JdbcService service = services.get(dbKey);

		if (service == null) {
			service = new JdbcService(dbKey);
			services.put(dbKey, service);
		}
		return service;

	}
    /**
     * 获取默认的JdbcDao (KEY="default")
     * @return
     */
	public static JdbcService getJdbcService() {

		JdbcService service = services.get(DATA.DEFAULT_KEY);

		if (service == null) {
			service = new JdbcService();
			services.put(DATA.DEFAULT_KEY, service);
		}
		return service;

	}
}
