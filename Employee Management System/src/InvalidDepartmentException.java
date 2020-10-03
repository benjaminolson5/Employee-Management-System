package com.cognixia.jump.javafinal.ems;

public class InvalidDepartmentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidDepartmentException() {
		super("You tried to assign an employee to a department that does not exist");
	}

}
