package com.sooncode.soonjdbc.dao.polymerization;

/**
 * 聚合
 * 
 * @author hechenwe@gmail.com
 *
 */
public class Polymerization {

	private String key;

	private Polymerization(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	public static final Polymerization SUM = new Polymerization("SUM");
	public static final Polymerization AVG = new Polymerization("AVG");
	public static final Polymerization COUNT = new Polymerization("COUNT");
	public static final Polymerization MAX = new Polymerization("MAX");
	public static final Polymerization MIN = new Polymerization("MIN");

}
