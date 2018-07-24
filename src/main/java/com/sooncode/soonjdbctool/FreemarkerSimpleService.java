package com.sooncode.soonjdbctool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * Freemarker 简单处理服务
 * 
 * @author chenhe9
 *
 */
public class FreemarkerSimpleService {

	/**
	 * 根据模板生成文件并输出
	 * 
	 * @param templatePath
	 * @param templateName
	 * @param outPath
	 * @param outFileName
	 * @param data
	 * @param encoding
	 * @throws Exception
	 */
	public static void fileOutput(String templatePath, String templateName, String outPath, String outFileName, Map<String, Object> data, String encoding) throws Exception {

		Configuration configuration = new Configuration();
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		configuration.setDefaultEncoding(encoding);
		File tempfile = new File(templatePath);// 模板所在的位置
		configuration.setDirectoryForTemplateLoading(tempfile);
		Template template = configuration.getTemplate(templateName);// 模板名称

		File folder = new File(outPath); // 输出文件所在的位置

		boolean isExist = folder.exists();
		if (isExist == false) { // 该文件夹不存在
			folder.mkdirs(); // 创建文件夹
			isExist = true;
		}
		if (isExist) {
			File file = new File(outPath + "/" + outFileName);// 要输出的文件
			Writer riter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
			template.process(data, riter);
			riter.flush();
			riter.close();

		}
	}

	/**
	 * 
	 * @param templatePath
	 * @param templateName
	 * @param data
	 * @param encoding
	 * @return
	 */
	public static String getProcessString(String templatePath, String templateName, Map<String, Object> data, String encoding) {

		Configuration configuration = new Configuration();
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		configuration.setDefaultEncoding(encoding);
		File tempfile = new File(templatePath);// 模板所在的位置
		try {
			configuration.setDirectoryForTemplateLoading(tempfile);
			Template template = configuration.getTemplate(templateName);// 模板名称
			Writer riter = new StringWriter();
			template.process(data, riter);
			riter.flush();
			riter.close();
			String re = riter.toString();
			return re;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	 

	public static String getProcessString(String templateString, Map<String, Object> dataMap, String encoding) {
		final String templateName="TEMP_NAME";
		Configuration configuration = new Configuration();
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate(templateName, templateString);
		configuration.setTemplateLoader(stringLoader);
		try {
			Template template = configuration.getTemplate(templateName, encoding);
			StringWriter writer = new StringWriter();
			template.process(dataMap, writer);
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {

		String templateContent = "欢迎：${name}";

		Map<String, Object> root = new HashMap<>();
		root.put("name", "九分裤打算累计亏损的");

		String str = getProcessString(templateContent, root, "utf-8");
		System.out.println(str);

	}

}
