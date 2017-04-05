package com.sooncode.soonjdbc.exception;

/**
 * 数据库表关系分析异常
 * 
 * @author he chen
 *
 */
public class TableRelationAnalyzeException extends Exception  {
 
	private static final long serialVersionUID = 9075974551814501636L;
	public TableRelationAnalyzeException(String message){
		super(message);
	}
}
