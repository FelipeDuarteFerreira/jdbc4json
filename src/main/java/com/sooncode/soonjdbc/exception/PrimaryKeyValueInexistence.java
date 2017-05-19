package com.sooncode.soonjdbc.exception;
/**
 * SQL 语句异常
 * @author pc
 *
 */
public class PrimaryKeyValueInexistence extends Exception {
	private static final long serialVersionUID = 614301440846576181L;
 
	public PrimaryKeyValueInexistence(String message){
		super(message);
	}
}
