package com.sooncode.type;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class TypesManager implements ApplicationContextAware {

	private static Map<String, Type> typesMap = new HashMap<>();

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, Type> types = applicationContext.getBeansOfType(Type.class);
		for(Entry<String, Type> en : types.entrySet()) {
			Type type = en.getValue();
			typesMap.put(type.getTypeCode(), type);
		}
	}

	public  Type getType(String code) {
		return typesMap.get(code);
	}

}
