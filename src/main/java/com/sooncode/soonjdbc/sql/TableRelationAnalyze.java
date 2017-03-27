package com.sooncode.soonjdbc.sql;

import java.util.List;

import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.bean.ForeignKey;

/**
 * 数据库 表关系 分析
 * 
 * @author pc
 *
 */
public class TableRelationAnalyze {

	public static boolean isOne(DbBean leftDbBean, DbBean... otherBeans) {
		if (leftDbBean != null && otherBeans.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 分析是否 是"一对一"关系
	 * 
	 * @param leftDbBean
	 * @param otherBeans
	 * @return
	 */
	public static boolean isOne2One(DbBean leftDbBean, DbBean... otherBeans) {

		if (leftDbBean == null || otherBeans == null || otherBeans.length == 0) {
			return false;
		}

		String leftDbName = leftDbBean.getBeanName().toUpperCase();
		boolean b = true;
		for (DbBean dbBean : otherBeans) {
			List<ForeignKey> fkes = dbBean.getForeignKeies();
			boolean bool = false;
			for (ForeignKey fk : fkes) {
				String referDbBeanName = fk.getReferDbBeanName().toUpperCase();
				boolean isUnique = fk.isUnique();
				if (leftDbName.equals(referDbBeanName) && isUnique == true) {
					bool = true;
				}
			}
			b = b && bool;
			if (bool == false) {
				break;
			}
		}

		if (b == true) {
			return true;
		} else {
			List<ForeignKey> leftFkes = leftDbBean.getForeignKeies();
			int n = 0;
			for (ForeignKey fk : leftFkes) {
				String referDbBeanName = fk.getReferDbBeanName().toUpperCase();
				for (DbBean dbBean : otherBeans) {
					String dbBeanName = dbBean.getBeanName().toUpperCase();
					if (dbBeanName.equals(referDbBeanName)) {
						n++;
					}
				}

			}
			if(n>0 ){
				return true;
			}else{
				return false;
			}
		}
 
	}

	/**
	 * 分析是否 是"一对多"关系
	 * 
	 * @param leftDbBean
	 * @param otherBeans
	 * @return
	 */
	public static boolean isOne2Many(DbBean leftDbBean, DbBean... otherBeans) {

		if (leftDbBean == null || otherBeans == null || otherBeans.length == 0) {
			return false;
		}

		String leftDbName = leftDbBean.getBeanName().toUpperCase();
		boolean b = true;
		for (DbBean dbBean : otherBeans) {
			List<ForeignKey> fkes = dbBean.getForeignKeies();
			boolean bool = false;
			for (ForeignKey fk : fkes) {
				String referDbBeanName = fk.getReferDbBeanName().toUpperCase();
				boolean isUnique = fk.isUnique();
				if (leftDbName.equals(referDbBeanName) && isUnique == false) {
					bool = true;
				}
			}
			b = b && bool;
			if (bool == false) {
				break;
			}
		}
		return b;
	}

	/**
	 * 分析是否是"多对多"关系
	 * 
	 * @param leftDbBean
	 * @param otherBeans
	 * @return
	 */
	public static boolean isMany2Many(DbBean leftDbBean, DbBean... otherBeans) {
		if (leftDbBean == null || otherBeans == null || otherBeans.length != 2) {
			return false;
		}
		String leftDbBeanName = leftDbBean.getBeanName().toUpperCase();
		String rightDbBeanName = otherBeans[1].getBeanName().toUpperCase();
		List<ForeignKey> middleFkes = otherBeans[0].getForeignKeies();
		int n = 0;
		for (ForeignKey fk : middleFkes) {
			String referDbBeanName = fk.getReferDbBeanName().toUpperCase();
			if (fk.isUnique() == false  &&  (referDbBeanName.equals(leftDbBeanName) || referDbBeanName.equals(rightDbBeanName))) {
				n++;
			}
		}
		if (n == 2) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 分析是否是"多对多"关系
	 * 
	 * @param leftDbBean
	 * @param otherBeans
	 * @return
	 */
	public static boolean isOne2Many2Many(DbBean leftDbBean, DbBean middleDbBean,DbBean rightDbBean) {
		if (leftDbBean == null || middleDbBean == null || rightDbBean == null) {
			return false;
		}
		String leftDbBeanName = leftDbBean.getBeanName().toUpperCase();
		String middleDbBeanName = middleDbBean.getBeanName().toUpperCase();
		List<ForeignKey> mFkes = middleDbBean.getForeignKeies();
		List<ForeignKey> rFkes = rightDbBean.getForeignKeies();
		 
		for (ForeignKey mf : mFkes) {
			if(mf.getReferDbBeanName().toUpperCase().equals(leftDbBeanName)){
				for (ForeignKey rf : rFkes) {
					if(rf.getReferDbBeanName().toUpperCase().equals(middleDbBeanName)){
						return true;
					}
				}
			}
		}
		return false;
	}
}
