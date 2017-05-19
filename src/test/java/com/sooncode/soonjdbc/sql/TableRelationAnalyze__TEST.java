package com.sooncode.soonjdbc.sql;

import org.junit.Test;

import com.sooncode.soonjdbc.bean.DbBean;

public class TableRelationAnalyze__TEST {

	
	
	@Test 
	public void isOne2One(){
		TableRelationAnalyze.isOne2One(null);
		TableRelationAnalyze.isOne2One(null, null,null);
		TableRelationAnalyze.isOne2One(new DbBean(), null,null);
		TableRelationAnalyze.isOne2One(null, new DbBean(),null);
		TableRelationAnalyze.isOne2One(null, null,new DbBean());
		TableRelationAnalyze.isOne2One(null, new DbBean(),new DbBean());
		TableRelationAnalyze.isOne2One(new DbBean(), new DbBean(),new DbBean());
	}
}
