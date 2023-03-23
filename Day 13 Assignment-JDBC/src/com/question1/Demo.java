package com.question1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Demo {
	static Connection con;
	static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		ResourceBundle rs = ResourceBundle.getBundle("dbdetails");
		return DriverManager.getConnection(rs.getString("url"),rs.getString("username"),rs.getString("password"));
	}
	static {
		try {
			con = getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static void closeConnection() throws SQLException {
		con.close();
	}
	
	
	static void insertCustomer(Scanner sc) throws SQLException {
		
		
		System.out.print("Enter Customer Id: ");
		String id = sc.nextLine();
		System.out.print("Enter Customer Name: ");
		String name = sc.nextLine();
		System.out.print("Enter Customer Gender: ");
		String gender = sc.nextLine();
		System.out.print("Enter Wallet Balance: ");
		Double balance = Double.parseDouble(sc.nextLine());
		System.out.print("Enter Date of Joining: ");
		LocalDate dateOfJoining = LocalDate.parse(sc.nextLine());
	
		
		Statement st = con.createStatement();
		int row = st.executeUpdate("INSERT INTO customer VALUES('"+ id +"','"+ name +"'"
				+ ",'"+ gender +"',"+ balance +",'"+ dateOfJoining +"')");
		if(row>0) 
			System.out.println("Customer Added Successfully.");
		   else
			System.out.println("Customer Couldn't added.");
	}
	
	static void viewCustomerList() throws SQLException {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM customer");
		int count=1;
		while(rs.next()) {
			System.out.println("Customer "+count+" Details");
			System.out.println("---------------");
			System.out.println("Customer Id: "+rs.getString(1));
			System.out.println("Customer Name: "+rs.getString(2));
			System.out.println("Customer Gender: "+rs.getString(3));
			System.out.println("Customer Walltet Balance: "+rs.getDouble(4));
			System.out.println("Customer Joining Date: "+rs.getDate(5));
			System.out.println("-----------------------------------------");
			count++;
		}
	}
	
	
	static void deleteCustomer(Scanner sc) throws SQLException {
		System.out.println("1. For delete by customer id");
		System.out.println("2. For delete Customer whose wallet balance is 0.0");
		System.out.print("Enter your Choice");
		int choice = Integer.parseInt(sc.nextLine());
		
		if(choice==1) {
			
			System.out.println("Enter Customer Id: ");
			String id = sc.nextLine();
			Statement st = con.createStatement();
			int row = st.executeUpdate("DELETE FROM customer where customerId = '"+ id +"'");
		    if(row>0) {
		    	System.out.println("Customer deleted successfully with Id "+id);
		    }else {
		    	System.out.println("No Customer Found with Id "+id);
		    }
		}else if(choice ==2) {
			Statement st = con.createStatement();
			int row = st.executeUpdate("DELETE FROM customer where wallet_balance=0.0");
			if(row>0) {
				System.out.println("Customer Deleted successfully");
			}else {
				System.out.println("No Customer found.");
			}
			
		}else {
			System.out.println("Invalid Input.");
			deleteCustomer(sc);
		}
		
		
	}

	
	static void updateCustomerDetail(Scanner sc) throws SQLException {
		System.out.print("Enter the Customer Id ");
		String id = sc.nextLine();
		
		System.out.print("Enter balance to add in wallet ");
		Double balance = Double.parseDouble(sc.nextLine());
		
		System.out.print("Enter date of joining for changing ");
		LocalDate dateOfJoining = LocalDate.parse(sc.nextLine());
		
		Statement st = con.createStatement();
		int row = st.executeUpdate("UPDATE customer SET wallet_balance=wallet_balance+"+ balance 
				+" , date_of_joining = '"+ dateOfJoining +"' WHERE customerId ='"+ id +"'");
		if(row>0) {
			System.out.println("Customer Updated Successfully..");
		}else {
			System.out.println("Customer Not Found.");
			updateCustomerDetail(sc);
		}
	}
	
	static void searchCustomerByJoiningDateRange(Scanner sc) throws SQLException {
		
		System.out.print("Enter Customer Date of Joining: ");
		LocalDate dateOfJoining = LocalDate.parse(sc.nextLine());
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM customer where date_of_joining BETWEEN '"+ dateOfJoining +"' AND '2023-03-31'");
		if(!rs.isBeforeFirst()&&rs.getRow()==0) {
			System.out.println("No Customer Found between this range");
		}else {
			while(rs.next()) {
				System.out.println("---------------");
				System.out.println("Customer Id: "+rs.getString(1));
				System.out.println("Customer Name: "+rs.getString(2));
				System.out.println("Customer Gender: "+rs.getString(3));
				System.out.println("Customer Walltet Balance: "+rs.getDouble(4));
				System.out.println("Customer Joining Date: "+rs.getDate(5));
				System.out.println("-----------------------------------------");
				
			}
		}
			
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Scanner sc = new Scanner(System.in);

		int choice = 0;
		do {
			System.out.println("1. Add Customer");
			System.out.println("2. Update Customer");
			System.out.println("3. Delete Customer");
			System.out.println("4. View Customer List");
			System.out.println("5. Search Customer By Date of Joining according to condition");
			System.out.println("0. Exit");
			
			System.out.print("Enter selection ");
			choice = Integer.parseInt(sc.nextLine());
			
			switch(choice) {
				case 1:
					insertCustomer(sc);
					break;
				case 2:
					updateCustomerDetail(sc);
					break;
				case 3:
					deleteCustomer(sc);
					break;
				case 4:
					viewCustomerList();
					break;
				case 5:
					searchCustomerByJoiningDateRange(sc);
					break;
				case 0:
					System.out.println("Thnaks for using our services");
					break;
				default:
					System.out.println("Invalid Selection, try again later");
			}			
		}while(choice != 0);
		sc.close();
		closeConnection();
		
	}

}
