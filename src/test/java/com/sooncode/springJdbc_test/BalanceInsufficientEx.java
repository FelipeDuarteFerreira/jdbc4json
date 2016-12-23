package com.sooncode.springJdbc_test;

public class BalanceInsufficientEx extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2841214647765222176L;

	public BalanceInsufficientEx() {

	}

	public BalanceInsufficientEx(String message) {
		super(message);
	}
}
