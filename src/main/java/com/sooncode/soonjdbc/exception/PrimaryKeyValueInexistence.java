package com.sooncode.soonjdbc.exception;
/**
 * PrimaryKeyValueInexistence
 * @author hechenwe@gmail.com
 *
 */
public class PrimaryKeyValueInexistence extends RuntimeException {
	private static final long serialVersionUID = 614301440846576181L;
 
	public PrimaryKeyValueInexistence(String message){
		super(message);
	}
}
