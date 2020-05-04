package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Payment {

	public static Connection con = null;
	
	public Connection connect() {
		if (con == null) {
		String dbURL = "jdbc:mysql://127.0.0.1:3306/helth_db?useSSL=false" ;
		//String dbName = "" ;
		String dbUsername = "root" ;
		String dbPassword = "kevinben10";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(dbURL,dbUsername , dbPassword);
		
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} 
		}
		return con;
	}
	
public String ViewPayment() {
	
	String output = "";
		
		try {
			Connection con = connect();
 			if (con == null)
 			{
 				return "Error while connecting to the database for reading.";
 			}
			String sql = "SELECT * FROM payment";
			Statement pst = con.createStatement();
			ResultSet rst = pst.executeQuery(sql); 
	         
					output = "<table border=\"1\">"
	     				+ "<tr>"
	     				+ "<th>payment ID<td>"	
	     				+ "<th>CardHolder Name<td>"	  
	     				+ "<th>type<td>"
	     				+ "<th>Amount<td>"
	     				+ "<th>Date<td>"
	     				+ "<th>CreditCard Number<td>"
	     				+ "<th>cvc<td>"   				 
	     				+ "</tr>";
					
			
	         while (rst.next()) {
             
	        	 	Integer paymentID = (rst.getInt("paymentID"));
	        	 	String cardHolderName = rst.getString("cardHolderName");
					String type = rst.getString("type");
					String amount = rst.getString("amount");
					String date = Double.toString(rst.getDouble("date"));
					String creditCardNumber = rst.getString("creditCardNumber");
					Integer cvc = rst.getInt("cvc");
					
	        	 
	        	 //html table1
					output += "<tr><td><input id=\"hidPaymentIDUpdate\""
							+ "name=\"hidPaymentIDUpdate\"type=\"hidden\""
							+ " value=\"" + paymentID + "\">"+ cardHolderName + "</td>";
					output += "<td>" + type + "</td>";
					output += "<td>" + amount + "</td>";
					output += "<td>" + date + "</td>";
					output += "<td>" + creditCardNumber + "</td>";
					output += "<td>" + cvc + "</td>";
	        	 
	        	// buton
	        	 output += "<td><input name=\"btnUpdate\""
	        	 		+ "type=\"button\" value=\"Update\""
	        	 		+ "class=\" btnUpdate btn btn-secondary\">"
	        	 		+ "</td><td><form method=\"post\" action=\"payment.jsp\">"
	        	 		+ "<input name=\"btnRemove\" type=\"submit\"value=\"Remove\""
	        	 		+ " class=\"btn btn-danger\"><input name=\"hidPaymentIDDelete\" "
	        	 		+ "type=\"hidden\"value=\"" + paymentID + "\">" + "</form></td></tr>";
	     		
	         }
	         con.close();
	         output += "</table>";
			} catch (SQLException e) {
				 output = "Error";
				System.err.println(e.getMessage());
			}
              
		
		return output;

	}

public String addPayment(int paymentID, String type, float amount,String date, String creditCardNumber, int cvc , String cardHolderName )
{

	String e1 = null;

	try { 

		String sql = "INSERT INTO payment(type,amount,date, creditCardNumber ,cvc , cardHolderName) "
				+ "VALUE(?,?,?,?,?,?)";
		

		PreparedStatement pst = con.prepareStatement(sql);

		pst.setString(1, type);
		pst.setFloat(2, amount);
		pst.setString(3, date);
		pst.setString(4, creditCardNumber);
		pst.setInt(5, cvc);
		pst.setString(6, cardHolderName);
	
		pst.executeUpdate();

		e1 = "payment details inserted";

	} catch (SQLException e) {

		e.printStackTrace();
	}
	return e1;

	}

public String UpdatePayment(int paymentID, String type, float amount,String date, String creditCardNumber, int cvc , String cardHolderName , String req_stat_cancl) {

	String e = null;
	
	
	try {
		
		String sql = "UPDATE payment SET type = ?,amount = ?,date = ?,creditCardNumber = ?,cvc = ?, cardHolderName = ?,req_stat_cancl = ? WHERE paymentID =?";
					
	 

		PreparedStatement pst;
		pst = con.prepareStatement(sql);

		pst.setString(1, type);
		pst.setFloat(2, amount);
		pst.setString(3, date);
		pst.setString(4, creditCardNumber);
		pst.setInt(5, cvc);
		pst.setString(6, cardHolderName);
		pst.setInt(7, paymentID);

		pst.executeUpdate();
		
		e = "payment details Updated";
		
		
	} catch (SQLException e1) {


		e1.printStackTrace();
	}

	

	return e;

	}

public String DeletePayment(int paymentID) {
	
	String status = null; 	
	
	try {
		String sql = "DELETE FROM payment "
					+ "WHERE paymentID = ? ";
		
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setInt(1, paymentID);
		pst.executeUpdate();			
		status = "payment "+paymentID+" deleted ";
		
	} catch (SQLException e) {
		 
		e.printStackTrace();
	}
	 
	
	return status;

	}
	
}

