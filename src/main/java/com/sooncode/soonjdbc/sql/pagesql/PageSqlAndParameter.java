package com.sooncode.soonjdbc.sql.pagesql;

import com.sooncode.soonjdbc.sql.Parameter;

public class PageSqlAndParameter {

	private Parameter selectParameter;
	private Parameter sizeParameter;

	public Parameter getSelectParameter() {
		return selectParameter;
	}

	public void setSelectParameter(Parameter selectParameter) {
		this.selectParameter = selectParameter;
	}

	public Parameter getSizeParameter() {
		return sizeParameter;
	}

	public void setSizeParameter(Parameter sizeParameter) {
		this.sizeParameter = sizeParameter;
	}

}
