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
		boolean isOne = (leftDbBean != null && otherBeans.length == 0);
		return isOne;

	}

	/**
	 * 分析是否 是"一对一"关系
	 * 
	 * @param leftDbBean
	 * @param otherBeans
	 * @return
	 */
	public static boolean isOne2One(DbBean leftDbBean, DbBean... otherBeans) {

		boolean otherBeansIsMany = false;
		boolean refer = true;
		boolean dbBeanIsHave = true;
		for (DbBean dbBean : otherBeans) {
			otherBeansIsMany = true;
			if (dbBean == null || leftDbBean == null) {
				dbBeanIsHave = false;
				break;
			}
			boolean isPassivityUniqueRefer = isPassivityUniqueRefer(leftDbBean, dbBean);
			boolean isRefer = isRefer(leftDbBean, dbBean);
			refer = refer && (isPassivityUniqueRefer || isRefer);
		}
		return otherBeansIsMany && dbBeanIsHave && refer;

	}

	/**
	 * 分析是否 是"一对多"关系
	 * 
	 * @param leftDbBean
	 * @param otherBeans
	 * @return
	 */
	public static boolean isOne2Many(DbBean leftDbBean, DbBean... otherBeans) {
		boolean otherBeansIsMany = false;
		boolean dbBeanIsHave = true;

		String leftDbName = leftDbBean.getTableName();
		boolean refer = true;
		for (DbBean dbBean : otherBeans) {
			otherBeansIsMany = true;
			if (dbBean == null || leftDbBean == null) {
				dbBeanIsHave = false;
				break;
			}

			List<ForeignKey> fkes = dbBean.getForeignKeies();
			boolean bool = false;
			for (ForeignKey fk : fkes) {
				String referDbBeanName = fk.getReferDbBeanName().toUpperCase();
				boolean isUnique = fk.isUnique();
				if (leftDbName.equals(referDbBeanName) && isUnique == false) {
					bool = true;
				}
			}
			refer = refer && bool;
			if (bool == false) {
				break;
			}
		}
		return otherBeansIsMany && dbBeanIsHave && refer;
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
		String leftDbBeanName = leftDbBean.getTableName();
		String rightDbBeanName = otherBeans[1].getTableName();
		List<ForeignKey> middleFkes = otherBeans[0].getForeignKeies();
		int n = 0;
		for (ForeignKey fk : middleFkes) {
			String referDbBeanName = fk.getReferDbBeanName().toUpperCase();
			if (fk.isUnique() == false && (referDbBeanName.equals(leftDbBeanName) || referDbBeanName.equals(rightDbBeanName))) {
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
	public static boolean isOne2Many2Many(DbBean leftDbBean, DbBean middleDbBean, DbBean rightDbBean) {
		if (leftDbBean == null || middleDbBean == null || rightDbBean == null) {
			return false;
		}
		String leftDbBeanName = leftDbBean.getTableName();
		String middleDbBeanName = middleDbBean.getTableName();
		List<ForeignKey> mFkes = middleDbBean.getForeignKeies();
		List<ForeignKey> rFkes = rightDbBean.getForeignKeies();

		for (ForeignKey mf : mFkes) {
			if (mf.getReferDbBeanName().toUpperCase().equals(leftDbBeanName)) {
				for (ForeignKey rf : rFkes) {
					if (rf.getReferDbBeanName().toUpperCase().equals(middleDbBeanName)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * leftDbBean 是否 被参照( 唯一 ) rightDbBean
	 * 
	 * @param leftDbBean
	 * @param rightDbBean
	 * @return
	 */
	private static boolean isPassivityUniqueRefer(DbBean leftDbBean, DbBean rightDbBean) {
		String leftDbName = leftDbBean.getTableName();
		List<ForeignKey> fkes = rightDbBean.getForeignKeies();
		boolean bool = false;
		for (ForeignKey fk : fkes) {
			String referDbBeanName = fk.getReferDbBeanName().toUpperCase();
			if (leftDbName.equals(referDbBeanName) && fk.isUnique() == true) {
				bool = true;
			}
		}
		return bool;
	}

	/**
	 * leftDbBean 是否参照 rightDbBean
	 * 
	 * @param leftDbBean
	 * @param rightDbBean
	 * @return
	 */
	private static boolean isRefer(DbBean leftDbBean, DbBean rightDbBean) {
		boolean bool = true;
		List<ForeignKey> leftFkes = leftDbBean.getForeignKeies();
		String rightTabeName = rightDbBean.getTableName();
		for (ForeignKey f : leftFkes) {
			String referDbBeanName = f.getReferDbBeanName().toUpperCase();
			if (rightTabeName.equals(referDbBeanName)) {
				bool = bool || true;
			}
		}
		return bool;
	}

}
