package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Payment {

	
	
	public Connection connect() {
		
		 Connection con = null;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/helth_db", "root" , "kevinben10");
		
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} 
		
		return con;
	}
	
public String ViewPayment() {
	
	String output = "";
		
		try {
			Connection con = connect();
 			if (con == null)
 			{
 				return "Error database reading.";
 			}
			String sql = "SELECT * FROM payment";
			Statement pst = con.createStatement();
			ResultSet rst = pst.executeQuery(sql); 
	         
					output = "<table border='3' class = 'table table-hover'>"
	     				+ "<tr>"
	     				+ "<th scope = 'col'> Card Holder Name</th>"
	     				+ "<th scope = 'col'> Amount </th>"
	     				+ "<th scope = 'col'> Credit Card Number </th>"
	     				+ "<th scope = 'col'> Year </th>"
	     				+ "<th scope = 'col'> Month </th>"
	     				+ "<th scope = 'col'> cvc </th>"   				 
	     				+ "<th scope = 'col'> Update </th><th scope = 'col'> Remove </th></tr>";
					
			
	         while (rst.next()) {
             
	        	 	String paymentID = Integer.toString(rst.getInt("paymentID"));
	        	 	String cardHolderName = rst.getString("cardHolderName");
					String amount = Double.toString(rst.getDouble("amount"));
					String creditCardNumber =  rst.getString("creditCardNumber");
					String year = Integer.toString(rst.getInt("year"));
					String month = Integer.toString(rst.getInt("month"));
					String cvc = Integer.toString(rst.getInt("cvc"));
					
	        	 
	        	 //html table1
					output += "<tr><td><input id='hidPaymentIDUpdate' name='hidPaymentIDUpdate' type='hidden' value='" + paymentID + "'>"+ cardHolderName + "</td>";
					output += "<td>" + amount + "</td>";
					output += "<td>" + creditCardNumber + "</td>";
					output += "<td>" + year + "</td>";
					output += "<td>" + month + "</td>";
					output += "<td>" + cvc + "</td>";
	        	 
	        	// buton
	        	 output += "<td><input name='btnUpdate' type= 'button' value='Update' class='btnUpdate btn btn-secondary'></td>"
	        	 		+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-paymentid='" + paymentID + "'>" + "</td></tr>";
	     		
	         }
	         con.close();
	         output += "</table>";
			} catch (SQLException e) {
				 output = "Error";
				System.err.println(e.getMessage());
			}
              
		
		return output;

	}

public String addPayment(String cardHolderName , String amount, String creditCardNumber , String year , String month ,String cvc)
{

	String e1 = "";

	try { 
		
		 Connection con =  connect();
		 
		 if (con == null)
		 {
			 return "Error while connectingto the database for inserting.";
		 } 

		String sql = "INSERT INTO payment(cardHolderName, amount, creditCardNumber, year , month ,cvc ) "
				+ "VALUE(?,?,?,?,?,?)";
		

		PreparedStatement pst = con.prepareStatement(sql);
		
		pst.setString(1, cardHolderName);
		pst.setDouble(2, Double.parseDouble(amount));
		pst.setString(3, creditCardNumber);
		pst.setInt(4, Integer.parseInt(year));
		pst.setInt(5, Integer.parseInt(month));
		pst.setInt(6, Integer.parseInt(cvc));
		
		pst.executeUpdate();
		
		String newPayment = ViewPayment();
		e1 = "{\"status\":\"success\", \"data\": \"" +newPayment + "\"}";


	} catch (SQLException e) {

		e1 = "{\"status\":\"error\", \"data\": \" Error \"}";
		System.err.println(e.getMessage());
	}
	return e1;

	}

public String UpdatePayment(String paymentID, String cardHolderName, String amount, String creditCardNumber , String year , String month, String cvc) {

	String e1 = null;
	
	
	try {
		
		Connection con = connect();
		 if (con == null)
		 {
		 return "Error while connecting to the database for updating.";
		 } 
		
		String sql = "UPDATE payment SET  cardHolderName = ? ,amount = ?,creditCardNumber = ?, year = ?, month = ?,cvc = ? WHERE paymentID =?";
					
	 

		PreparedStatement pst;
		pst = con.prepareStatement(sql);

		pst.setString(1, cardHolderName);
		pst.setDouble(2, Double.parseDouble(amount));
		pst.setString(3, creditCardNumber);
		pst.setInt(4, Integer.parseInt(year));
		pst.setInt(5, Integer.parseInt(month));
		pst.setInt(6, Integer.parseInt(cvc));
		pst.setInt(7, Integer.parseInt(paymentID));

		pst.executeUpdate();
		
		e1 = "payment details Updated";
		
		String newPayment = ViewPayment();
		e1 = "{\"status\":\"success\", \"data\": \"" +newPayment + "\"}";
		
		
	} catch (SQLException e) {

		e1 = "{\"status\":\"Error\", \"data\": \" Error \"}";
		System.err.println(e.getMessage());
	}

	return e1;

}

public String DeletePayment(String paymentID) {
	
	String output = ""; 	
	
	try {
		
		Connection con = connect();
		 if (con == null)
		 {
		 return "Error while connecting to the database for deleting.";
		 } 
		 
		String sql = "DELETE FROM payment WHERE paymentID=?";
		
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setInt(1, Integer.parseInt(paymentID));
		
		pst.executeUpdate();
		
		String newPayment = ViewPayment();
		output =  "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}"; 
		
	} catch (SQLException e) {
		 
		output = "{\"status\":\"error\", \"data\":\"Error while deleting the payment.\"}"; 
		System.err.println(e.getMessage());
	}
	 
	
	return output;

	}
	
}

