package com.sooncode.jdbc4json.reflect;

 

public class Temp <E>{
public String EclassName;
	 
public Temp(){
	this.EclassName = Genericity.getGenericity(this.getClass(), 0);
}
}
