package com.sooncode.soonjdbc.util.create_entity;

import java.io.File;

/**
 * 工程路径 工具类
 * 
 * @author pc
 *
 */
public class PathUtil {
	private static String classes;

	static {
		classes = new PathUtil().getPath();
	}

	public static String getClassPath() {
		// System.out.println("【classes路径】"+classes);
		return classes;

	}

	private String getPath() {
		String path = this.getClass().getResource("/").getPath();
		File file = new File(path);
		String classesPath = file.toString() + File.separatorChar;
		return classesPath;
	}

	public static void main(String[] args) {
		/*String path = new PathUtil().getClass().getResource("/").getPath();
		File file = new File(path);
		String classesPath = file.toString() + File.separatorChar;
		classesPath = classesPath.replace("%20", " ");*/

		 

		String jarFilePath = new PathUtil().getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
		File file = new File(jarFilePath);
		String classesPath = file.toString() + File.separatorChar;
		 
		System.out.println("PathUtil.main()" + classesPath);
	 

	}

}
