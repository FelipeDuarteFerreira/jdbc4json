package com.sooncode.soonjdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggerController<T> {

	 public static Log logger  ;
	 
	 public static void print(Class<?> clas, String message,String loggerLevel) {
		 logger= LogFactory.getLog(clas);
		 if(loggerLevel.equals("debug")) {
			 logger.debug(message); 
		 }
		 if(loggerLevel.equals("info")) {
			 logger.info(message); 
		 }
		 if(loggerLevel.equals("warn")) {
			 logger.warn(message);  
		 }
		  
		 
	 }
}
