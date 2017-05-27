package com.sooncode.soonjdbc.sql.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.ResourceUtils;

public class SqlXmlManager {

	static Map<String, File> fielMap = new HashMap<>();

	public void setXmlFileLocation(String xmlFileLocation) {

		try {
			File f = ResourceUtils.getFile(xmlFileLocation);
			String[] fileNames = f.list();
			for (String fileName : fileNames) {
				File file = ResourceUtils.getFile(xmlFileLocation + File.separatorChar + fileName);
				fielMap.put(fileName, file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
