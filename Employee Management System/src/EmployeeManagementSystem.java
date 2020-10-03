package com.cognixia.jump.javafinal.ems;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class EmployeeManagementSystem {

	public static void main(String[] args) {
		System.out.println("Welcome to the Employee Management System...");
		
		Scanner scan = new Scanner(System.in);
		List<Employee> emp = new ArrayList<>();
		
		//This is the starting file loading in the employee information.
		readOnLaunch(emp);
		
		int option;
		boolean showMenu = true;
		while(showMenu){
			System.out.println("\nSelect an option from the list...");
			System.out.println("0) View Single Employee 1) View Employees 2) View Departments 3) Add New Employee 4) Update Employee 5) Remove Employee 6) Sorting Options 7) Save 8) Reload Last Save 9) Save & Exit 10) Exit Without Saving");
			try {
				option = scan.nextInt();
				switch(option) {
					case 9:
						writeCSV(emp);
						System.out.println("emp.txt Overwritten. Thank you for using the Employee Management System. Goodbye!");
						showMenu = false;
						break;
					case 10:
						System.out.println("Thank you for using the Employee Management System. Goodbye!");
						showMenu = false;
						break;
					case 0:
						System.out.println("Enter Employee ID: ");
						int empId = scan.nextInt();
						boolean exists = false;
						for(Employee e : emp) {
							if(e.getId() == empId) {
								System.out.println(e);
								exists = true;
								break;
							}
						}
						if(!exists) {
							System.out.println("No Employee with ID: " + empId);
						}
						break;
					case 1:
						System.out.println(emp);
						break;
					case 2:
						System.out.println("Departments:");
						for(Employee.Departments d : Employee.Departments.values()) {
							System.out.print(d + " ");
						}
						System.out.println();
						break;
					case 3:
						scan.nextLine();
						createNewEmployee(emp, scan);
						break;
					case 4:
						System.out.println("Enter Employee ID: ");
						updateEmployee(emp, scan.nextInt(), scan);
						break;
					case 5:
						System.out.println("Enter Employee ID: ");
						removeEmployee(emp, scan.nextInt());
						break;
					case 6: //Sorting methods.
						System.out.println("Sort by: 1) Name 2) ID 3) Department 4) Salary ASC 5) Salary DESC 6) Age ASC 7) Age DESC 0) Back");
						int sortOption = scan.nextInt();
						switch (sortOption) {
							case 0:
								break;
							case 1: 
								emp = sortName(emp);
								break;
							case 2:
								emp = sortId(emp);
								break;
							case 3: 
								emp = sortDepartment(emp);
								break;
							case 4: 
								emp = sortSalaryAscending(emp);
								break;
							case 5: 
								emp = sortSalaryDescending(emp);
								break;
							case 6: 
								emp = sortYoungesttoOldest(emp);
								break;
							case 7: 
								emp = sortOldestToYoungest(emp);
								break;
							default:
								System.out.println("Invalid Menu Option. Try Again...");
								break;
						}
					case 7:
						writeCSV(emp);
						break;
					case 8:
						readCSV(emp);
						break;
					default:
						System.out.println("Invalid Menu Option. Try Again...");
						break;
					}
			} catch (InputMismatchException e) {
				System.out.println("Enter Selection as a Number from the Options...");
			} catch (Exception e) {
				System.out.println("Enter Selection as a Number from the Options...");
			} finally {
				scan.nextLine(); //Clear the scanner.
			}
		}
		
		scan.close();
	}
	
	//This method will have you enter a List along with an employee ID, and edits the information of the given employee.
	public static void updateEmployee(List<Employee> emp, int id, Scanner in) {
	
		Employee.Departments dept = Employee.Departments.INTERN;
		
		while(true) {
			System.out.println("Edit 1) Name 2) Age 3) ID 4) Department 5) Salary 0) Finished");
			try {
				int option = in.nextInt();
				
				
				switch(option) {
					case 0:
						return;
					case 1:
						in.nextLine();
						System.out.println("Enter new name: ");
						for(Employee e : emp) {
							if(e.getId() == id) {
								e.setName(in.nextLine());
							}
						}
						break;
					case 2:
						System.out.println("Enter new age: ");
						for(Employee e : emp) {
							if(e.getId() == id) {
								e.setAge(in.nextInt());
							}
						}
						break;
					case 3:
						System.out.println("Enter new ID: ");
						for(Employee e : emp) {
							if(e.getId() == id) {
								e.setId(in.nextInt());
							}
						}
						break;
					case 4:
						System.out.println("Enter New Employee Department Number (SALES=0, HR=1, FINANCE=2, MANAGEMENT=3, IT=4, MARKETING=5, INTERN=6): ");
						try {
							int deptOption = in.nextInt();
							
							switch(deptOption) {
								case 0:
									dept = Employee.Departments.SALES;
									break;
								case 1:
									dept = Employee.Departments.HR;
									break;
								case 2:
									dept = Employee.Departments.FINANCE;
									break;
								case 3:
									dept = Employee.Departments.MANAGEMENT;
									break;
								case 4:
									dept = Employee.Departments.IT;
									break;
								case 5:
									dept = Employee.Departments.MARKETING;
									break;
								case 6:
									dept = Employee.Departments.INTERN;
									break;
								default:
									throw new InvalidDepartmentException();
							} 
							
							for(Employee e : emp) {
								if(e.getId() == id) {
									e.setDepartment(dept);
								}
							}	
							
							break;
							
						}catch (InvalidDepartmentException e) {
							in.nextLine();
							System.out.println(e.getMessage() + ", try again...");
						} catch (IllegalArgumentException e) {
							System.out.println("Invalid Department, try again...");
						} catch(InputMismatchException e) {
							in.nextLine();
							System.out.println("Input mismatch, try again...");
						} catch(Exception e) {
							in.nextLine();
							System.out.println("Exception caught, try again...");
						}
						break;
					case 5:
						System.out.println("Enter New Salary: ");
						for(Employee e : emp) {
							if(e.getId() == id) {
								e.setSalary(in.nextInt());
							}
						}
						break;
					default:
						System.out.println("Invalid Menu Option. Try Again...");
						break;
					}
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid Input, try again...");
			} catch(InputMismatchException e) {
				in.nextLine();
				System.out.println("Input mismatch, try again...");
			} catch(Exception e) {
				in.nextLine();
				System.out.println("Exception caught, try again...");
			}
		}
		
	}
	
	//This method requires the user to input an employee ID in order to remove an employee from employees.txt
	public static void removeEmployee(List<Employee> emp, int id){
	    
		Iterator<Employee> it = emp.iterator();
		
	    while(it.hasNext()){
	        Employee employee = it.next();
			
			if(employee.getId() == id){
				it.remove();
	            System.out.println("Removed Employee with ID: " + id);
	            return;
	        }
	    }
	    System.out.println("No Employee with ID: " + id);
	}
	
	//This method allows for the employee.txt file to be read.
	public static void readOnLaunch(List<Employee> emp) {
		File launchData = new File("resources/employees.txt");
		if(!launchData.exists()) {
			System.out.println("employees.txt is missing... terminating.");
			System.exit(0);
		}
		FileReader fileReader = null;
		BufferedReader buffReader = null;
		
		try {
			fileReader = new FileReader(launchData);
			buffReader = new BufferedReader(fileReader);
			
			String line = buffReader.readLine();
			
			
			while(line != null) { //Runs until there are no more employees in the file
				String[] parts = line.split(", ");
				
				try {
					emp.add(new Employee(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Enum.valueOf(Employee.Departments.class, parts[3]), Integer.parseInt(parts[4])));
				}
				catch (InputMismatchException e) {
					System.out.println("File formatted incorrectly.");
				}
				catch (NumberFormatException e) {
					System.out.println("File formatted incorrectly.");
				}
		
				line = buffReader.readLine();
			}
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} catch (IOException e) {
			System.out.println("File could not be read.");
		} finally { //Closes the reader
			try {
				if(buffReader != null) buffReader.close();
				if(fileReader != null) fileReader.close();
			} catch (IOException e) {
				System.out.println("File could not be closed.");
			}
		}
	}
	
	//This will take a list as an arguement and read employee infomration onto it, formating the employee information so it can be entered as an index.
	public static void readCSV(List<Employee> emp) {
		File file = new File("resources/emp.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Could not create file");
			}
		}
		FileReader fileReader = null;
		BufferedReader buffReader = null;
		
		try {
			fileReader = new FileReader(file);
			buffReader = new BufferedReader(fileReader);
			
			String line = buffReader.readLine();
			
			
			while(line != null) {
				String[] parts = line.split(", ");
				
				try {
					emp.add(new Employee(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Enum.valueOf(Employee.Departments.class, parts[3]), Integer.parseInt(parts[4])));
				}
				catch (InputMismatchException e) {
					System.out.println("File formatted incorrectly.");
				}
				catch (NumberFormatException e) {
					System.out.println("File formatted incorrectly.");
				}
		
				line = buffReader.readLine();
			}
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} catch (IOException e) {
			System.out.println("File could not be read.");
		} finally {
			try {
				if(buffReader != null) buffReader.close();
				if(fileReader != null) fileReader.close();
			} catch (IOException e) {
				System.out.println("File could not be closed.");
			}
		}
	}
	//This will take a list as an arguemnet and seperate the employee infomation into lines and then write the information into the text file once it has been reformated with comas seperating the information.
	public static void writeCSV(List<Employee> emp) {
		File file = new File("resources/emp.txt");
		if(!file.exists()) { 
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Could not create file");
			}
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter pw = null;
		
		
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			
			String comma = ", ";
			
			for(Employee e : emp) {
				pw.print(e.getName() + comma + e.getAge() + comma + e.getId() + comma + e.getDepartment() + comma + e.getSalary());
				pw.println();
			}
			
		} catch (IOException e) {
			System.out.println("File Could Not Be Written To");
		} finally {
			try {
				if(pw != null) pw.close();
				if(bw != null) bw.close();
				if(fw != null) fw.close();
			} catch (IOException e) {
				System.out.println("File Could Not Be Closed");
			}
		}
	}
	
	//This method prompts the user to enter new employee information using the Scanner class. Information entered includes name, age, id, department and salary.
	public static void createNewEmployee(List<Employee> emp, Scanner in) {
		String name = "";
		int age, id, salary = 0;
		Employee.Departments dept = Employee.Departments.INTERN;
		
		//Name
		while(true) {
			System.out.println("Enter Employee Name: ");
			try {
				name = in.nextLine();
				break;
			} catch(InputMismatchException e) {
				in.nextLine();
				System.out.println("Input mismatch, try again...");
			} catch(Exception e) {
				in.nextLine();
				System.out.println("Exception caught, try again...");
			}
		}
		//age
		while(true) {
			System.out.println("Enter Employee Age: ");
			try {
				age = in.nextInt();
				break;
			} catch(InputMismatchException e) {
				in.nextLine();
				System.out.println("Input mismatch, try again...");
			} catch(Exception e) {
				in.nextLine();
				System.out.println("Exception caught, try again...");
			}
		}
		//id
		while(true) {
			System.out.println("Enter Employee ID: ");
			try {
				id = in.nextInt();
				break;
			} catch(InputMismatchException e) {
				in.nextLine();
				System.out.println("Input mismatch, try again...");
			} catch(Exception e) {
				in.nextLine();
				System.out.println("Exception caught, try again...");
			}
		}
		//department
		while(true) {
			System.out.println("Enter Employee Department Number (SALES=0, HR=1, FINANCE=2, MANAGEMENT=3, IT=4, MARKETING=5, INTERN=6): ");
			try {
				int option = in.nextInt();
				
				switch(option) {
					case 0:
						dept = Employee.Departments.SALES;
						break;
					case 1:
						dept = Employee.Departments.HR;
						break;
					case 2:
						dept = Employee.Departments.FINANCE;
						break;
					case 3:
						dept = Employee.Departments.MANAGEMENT;
						break;
					case 4:
						dept = Employee.Departments.IT;
						break;
					case 5:
						dept = Employee.Departments.MARKETING;
						break;
					case 6:
						dept = Employee.Departments.INTERN;
						break;
					default:
						throw new InvalidDepartmentException();
				}
				break;
			} catch (InvalidDepartmentException e) {
				in.nextLine();
				System.out.println(e.getMessage() + ", try again...");
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid Department, try again...");
			} catch(InputMismatchException e) {
				in.nextLine();
				System.out.println("Input mismatch, try again...");
			} catch(Exception e) {
				in.nextLine();
				System.out.println("Exception caught, try again...");
			}
		}
		//salary
		while(true) {
			System.out.println("Enter Employee Salary: ");
			try {
				salary = in.nextInt();
				break;
			} catch(InputMismatchException e) {
				in.nextLine();
				System.out.println("Input mismatch, try again...");
			} catch(Exception e) {
				in.nextLine();
				System.out.println("Exception caught, try again...");
			}
		}
		
		emp.add(new Employee(name, age, id, dept, salary));
		
	}
	
	//Alphabetical sorting of the name
	public static List<Employee> sortName(List<Employee> emp) {
		List<Employee> temp = emp.stream().sorted(Comparator.comparing(Employee::getName)).collect(Collectors.toList());
		return temp;
		
	}
	//Numerical (Descending) sorting of salary
	public static List<Employee> sortSalaryDescending(List<Employee> emp) {
		List<Employee> temp = emp.stream().sorted(Comparator.comparing(Employee::getSalary)).collect(Collectors.toList());
		return temp;
		
	}
	//Numerical (Ascending) sorting of salary
	public static List<Employee> sortSalaryAscending(List<Employee> emp) {
		List<Employee> temp = emp.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).collect(Collectors.toList());
		return temp;
			
		}
	//Finding all employees in a specific department 
	public static List<Employee> sortDepartment(List<Employee> emp) {
		List<Employee> temp = emp.stream().sorted(Comparator.comparing(Employee::getDepartment)).collect(Collectors.toList());
		return temp;
			
	}
	//Numerical (Ascending) sorting of age 
	public static List<Employee> sortYoungesttoOldest(List<Employee> emp) {
		List<Employee> temp = emp.stream().sorted(Comparator.comparing(Employee::getAge)).collect(Collectors.toList());
		return temp;
		
	}
	//Numerical (Descending) sorting of age 
	public static List<Employee> sortOldestToYoungest(List<Employee> emp) {
		List<Employee> temp = emp.stream().sorted(Comparator.comparing(Employee::getAge).reversed()).collect(Collectors.toList());
		return temp;
	}
	//Numerical (Ascending) sorting of id
	public static List<Employee> sortId(List<Employee> emp) {
		List<Employee> temp = emp.stream().sorted(Comparator.comparing(Employee::getId)).collect(Collectors.toList());
		return temp;
	}
	
}
