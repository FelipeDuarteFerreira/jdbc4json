package com.sooncode.jdbc4json;

import java.util.LinkedList;
import java.util.List;

import com.sooncode.jdbc4json.page.One2One;

public class TwoList<L,R> {

	 private List<Mo<L,R>> mos = new LinkedList<>();
	 
	 public void add(L l ,R r){
		 Mo<L,R> mo = new Mo<>();
		 mo.l = l;
		 mo.r = r;
		 mos.add(mo);
	 }
	 
	 public int size(){
		 return mos.size();
	 }
	 
	 public L getLeft(int index){
		 Mo<L,R> mo = mos.get(index);
		 return mo.l;
	 }
	 public R getRight(int index){
		 Mo<L,R> mo = mos.get(index);
		 return mo.r;
	 }
	 
	 
	 public static void main(String[] args) {
		 TwoList<String,Integer> list = new TwoList<>();
		
		list.add("hechen", 12);
		list.add("limi", 13);
		list.add("tom", 14);
		
		for (int i = 0;i<list.size();i++) {
			String name = list.getLeft(i);
			Integer age = list.getRight(i);
			
			System.out.println("name:"+ name +"   age:"+age);
		}
		
		
		
	}
}

 


class Mo<L,R>{
	public L l;
	
	public R r; 
	
}


class Pa {
	private One2One<Object ,Object> one ;
	
	@SuppressWarnings("unchecked")
	public <L,R> One2One<L,R> getOne2One (){
		
		return (One2One<L, R>) one;
	}
}