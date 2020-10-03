package com.cognixia.jump.javafinal.ems;

import java.io.Serializable;

public class Employee extends Person implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int salary;
	private Departments department;
	
	public enum Departments	{
		SALES, HR, FINANCE, MANAGEMENT, IT, MARKETING, INTERN;
	}
	
	public Employee(String name, int age, int id, Departments department, int salary) {
		super(name, age);
		this.id = id;
		this.department = department;
		this.salary = salary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Departments getDepartment() {
		return department;
	}

	public void setDepartment(Departments department) {
		this.department = department;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "\nEmployee [name= " + getName() + ", age= " + getAge() + ", id= " + id + ", salary= " + salary + ", department= " + department + "]";
	}
	
	
	
	
	
}
