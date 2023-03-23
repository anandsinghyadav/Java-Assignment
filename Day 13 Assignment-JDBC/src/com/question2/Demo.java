package com.question2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Demo {
	static Connection con;
	static Connection getConncetion() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		ResourceBundle rs = ResourceBundle.getBundle("dbdetails");
		return DriverManager.getConnection(rs.getString("url"),rs.getString("username"),rs.getString("password"));
	}
	
	static {
		try {
			con = getConncetion();
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
	 
	 static void addBook(Scanner sc) throws SQLException {
		 
		 System.out.print("Enter Book Id ");
		 int bookId = Integer.parseInt(sc.nextLine());
		 
		 System.out.print("Enter Book Name ");
		 String bookName = sc.nextLine();
		 
		 System.out.print("Enter Book Price ");
		 Double price = Double.parseDouble(sc.nextLine());
		 
		 System.out.print("Enter Book Author ");
		 String bookAuthor = sc.nextLine();
		 
		 System.out.print("Enter Book Publish Year ");
		 String publishYear = sc.nextLine();
		 
		 
		 Statement st = con.createStatement();
		 int row = st.executeUpdate("INSERT INTO book VALUES("+bookId+", '"+bookName+"',"+price+",'"+bookAuthor+"','"+publishYear+"')");
		 if(row>0) {
			 System.out.println("Book Inserted Successfully");
		 }else {
			 System.out.println("Failed to insert.");
		 }
		 
	 }
	 
	 static void searchBookByAuthorName(Scanner sc) throws SQLException {
		
		 System.out.print("Enter Author Name");
		 String name = sc.nextLine();
		 
		 Statement st =con.createStatement();
		 ResultSet rs =st.executeQuery("SELECT * FROM book where bookAuthor='"+ name +"'");
		 if(!rs.isBeforeFirst()&&rs.getRow()==0) {
			 System.out.println("No books found with Auther name "+name);
		 }else {
			 while(rs.next()) {
				 System.out.println("Book Id: "+rs.getInt(1));
				 System.out.println("Book Name: "+rs.getString(2));
				 System.out.println("Book Price: "+rs.getDouble(3));
				 System.out.println("Book Author: "+rs.getString(4));
				 System.out.println("Book Publish Year: "+rs.getString(5));
				 System.out.println("--------------------------------------");
				 
			 }
		 }
		 
	 }
	 
	 static void searchBookBetween1000And2000(Scanner sc) throws SQLException {
		 
		 Statement st=con.createStatement();
		 ResultSet rs =st.executeQuery("SELECT * FROM book where bookPrice BETWEEN 1000 AND 2000");
		 
		 if(!rs.isBeforeFirst()&& rs.getRow()==0) {
			 System.out.println("No Books Found");
		 }else {
			 while(rs.next()) {
				 System.out.println("Book Id: "+rs.getInt(1));
				 System.out.println("Book Name: "+rs.getString(2));
				 System.out.println("Book Price: "+rs.getDouble(3));
				 System.out.println("Book Author: "+rs.getString(4));
				 System.out.println("Book Publish Year: "+rs.getString(5));
				 System.out.println("--------------------------------------");
			 }
		 }
		 
	 }
	 
	 static void deleteBookById(Scanner sc) throws SQLException {
		 System.out.print("Enter Book Id: ");
		 int id = Integer.parseInt(sc.nextLine());
		 Statement st = con.createStatement();
		 int row = st.executeUpdate("DELETE FROM book where bookId ="+ id +"");
		 if(row>0) {
			 System.out.println("Book has deleted Successfully.");
		 }else {
			 System.out.println("No Book found with Id "+id);
		 }
	 }
	 
	 static void updateBookAuthorNameById(Scanner sc) throws SQLException {
		 System.out.print("Enter Book Id: ");
		 int id = Integer.parseInt(sc.nextLine());
		 
		 Statement st = con.createStatement();
		 
		 ResultSet rs = st.executeQuery("SELECT * from book WHERE bookId ="+ id +"");

		 if(!rs.isBeforeFirst()&&rs.getRow()==0) {
			 System.out.println("Invalid Author Name");
			 
		 }else {
			 while(rs.next()) {
				 System.out.println("Currently Book Author is "+rs.getString("bookAuthor"));
			 }
			 System.out.print("Enter New Author Name for updation: ");
			 String newName = sc.nextLine();
			 int row =st.executeUpdate("UPDATE book SET bookAuthor ='"+ newName +"' WHERE bookId="+ id +"");
			 if(row>0) {
				 System.out.println("Updated Successfully");
				
			 }else {
				 System.out.println("Not Updated.");
			 }
		 }
		 
	 }
	  
	 static void displayBooksInParticularYear(Scanner sc) throws SQLException {
		 
		 System.out.print("Enter Year to search published books");
		 String year = sc.nextLine();
		 
		 Statement st = con.createStatement();
		 ResultSet rs = st.executeQuery("SELECT * FROM book where publishYear='"+ year +"'");
		 if(!rs.isBeforeFirst()&& rs.getRow()==0) {
			 System.out.println("No Books published in year-"+year);
		 }else {
			 while(rs.next()) {
				 System.out.println("Book Id: "+rs.getInt(1));
				 System.out.println("Book Name: "+rs.getString(2));
				 System.out.println("Book Price: "+rs.getDouble(3));
				 System.out.println("Book Author: "+rs.getString(4));
				 System.out.println("Book Publish Year: "+rs.getString(5));
				 System.out.println("--------------------------------------");
			 }
		 }
		 
		 
	 }
	  
	 static void displayAllBooks(Scanner sc) throws SQLException {
		 Statement st = con.createStatement();
		 ResultSet rs = st.executeQuery("SELECT * FROM book");
		 
		 if(!rs.isBeforeFirst()&& rs.getRow()==0) {
			 System.out.println("No Books Found");
		 }else {
			 int count=1;
			 while(rs.next()) {
				 System.out.println("Details of Book "+count);
				 System.out.println("-------------");
				 System.out.println("Book Id: "+rs.getInt(1));
				 System.out.println("Book Name: "+rs.getString(2));
				 System.out.println("Book Price: "+rs.getDouble(3));
				 System.out.println("Book Author: "+rs.getString(4));
				 System.out.println("Book Publish Year: "+rs.getString(5));
				 System.out.println("--------------------------------------");
				 count++;
			 }
		 }
		 
	 }
	 
	 public static void main(String[] args) throws SQLException {
		
		 Scanner sc = new Scanner(System.in);
		 int choice =0;
		 
		 do {
			 System.out.println("1. Add Book");
			 System.out.println("2. Search Book By Author Name");
			 System.out.println("3. Delete Book By Id");
			 System.out.println("4. Update Book Author By Id");
			 System.out.println("5. Display Books published in Particular Year");
			 System.out.println("6. Display All Books");
			 System.out.println("7. Search Books Which has price between 1000 and 2000");
			 System.out.println("0. Exit");
			 
			 System.out.print("Enter Your Choice: ");
			 choice = Integer.parseInt(sc.nextLine());
			 switch(choice) {
			 case 1:
				 addBook(sc);
				 break;
			 case 2:
				 searchBookByAuthorName(sc);
				 break;
			 case 3: 
				 deleteBookById(sc);
				 break;
			 case 4: 
				 updateBookAuthorNameById(sc);
				 break;
			 case 5:
				 displayBooksInParticularYear(sc);
				 break;
			 case 6:
				 displayAllBooks(sc);
				 break;
			 case 7:
				 searchBookBetween1000And2000(sc);
			 case 0:
				 System.out.println("Thank you for Using.");
				 break;
			 default:
				 System.out.println("Enter Correct Choice:");
				 break;
			 }
			 
		 }while(choice!=0);
		 sc.close();
		 closeConnection();
		 
	}
	 

}
