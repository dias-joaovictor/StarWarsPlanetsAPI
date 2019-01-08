package br.com.thorntail.exception.manager;

import java.io.Serializable;

public class BusinessException extends Exception implements Serializable{

	private static final long serialVersionUID = -3220003514025085303L;

	public BusinessException() {
		super();
	}
	
	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Exception ex) {
		super(message, ex);
	}
	
	public BusinessException(Exception ex) {
		super("Internal error", ex);
	}

	
	
}
