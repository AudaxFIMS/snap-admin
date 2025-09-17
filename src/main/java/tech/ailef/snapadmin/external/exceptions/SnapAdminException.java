/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package tech.ailef.snapadmin.external.exceptions;

/**
 * Generic top-level exception for everything thrown by us
 *
 */
public class SnapAdminException extends RuntimeException {
	private static final long serialVersionUID = 8120227031645804467L;

	public SnapAdminException() {
	}
	
	public SnapAdminException(String msg, Throwable e) {
		super(msg, e);
	}
	
	public SnapAdminException(Throwable e) {
		super(e);
	}
	
	public SnapAdminException(String msg) {
		super(msg);
	}
}
