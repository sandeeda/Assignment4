/*************************************************************************************************
*  Course_Name – Assignment x                                                                                                                                *

*  I declare that this assignment is my own work in accordance with Humber Academic Policy.        *

*  No part of this assignment has been copied manually or electronically from any other source       *

*  (including web sites) or distributed to other students/social media.                                                       *
                                                                                                                                                                             
*  Name: Sandeep Das Student ID: N01472825 Date: 13th Mar 2022  		          
*/

package org.staff.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import org.humber.SQLInfo.OracleInfo;
import org.staff.entity.Staff;

public class StaffToolsDao {
	
	//Create a connection object
	private Connection connection;
	//Default constructor
	public StaffToolsDao() {
		// TODO Auto-generated constructor stub
		createConnection();
	}
	
	public boolean createConnection() {
		// TODO Auto-generated method stub
		//check if connection is not null, then close the connection
		if(connection!=null)
		{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
			
			try {
				//Load the class name using the DRIVER_CLASS_MYSQL static from OracleInfo class
				Class.forName(OracleInfo.DRIVER_CLASS_MYSQL);
				//Get a connection returned from Driver Manager using the url, username and password
				connection=DriverManager.getConnection(  
						OracleInfo.URL,OracleInfo.userName,OracleInfo.pwd);
				//Connection created
				System.out.println("Connection created");
				return true;
				
			} 
			//Catch clause to handle class not found exception
			catch (ClassNotFoundException classNotFoundException) {
				// TODO: handle exception
				classNotFoundException.printStackTrace();
				return false;
			} 
			//SQL exception handler
			catch (SQLException sqlException) {
				// TODO: handle exception
				sqlException.printStackTrace();
				return false;
			}
	}
	
	
	//Function to insert a record
	public void insertRecord(Staff staff) throws SQLIntegrityConstraintViolationException {
		try {
			//create a statement
			Statement statement = connection.createStatement();
			//Use a string to store the query for prepared statement
			String varname1 = ""
					+ "insert into staff (id, lastname, firstname, mi, address, city, state, telephone, email) "
					+ "values "
					+ "(?,?,?,?,?,?,?,?,?)";
			//prepare statement using the formed string
			PreparedStatement preparedStatement = connection.prepareStatement(varname1);
			
			//set the parameters of the prepared statement using the staff attributes of Staff object
			preparedStatement.setString(1, staff.getId());
			preparedStatement.setString(2, staff.getLastName());
			preparedStatement.setString(3, staff.getLastName());
			preparedStatement.setString(4, staff.getGender());
			preparedStatement.setString(5, staff.getAddress());
			preparedStatement.setString(6, staff.getCity());
			preparedStatement.setString(7, staff.getState());
			preparedStatement.setString(8, staff.getTelephone());
			preparedStatement.setString(9, staff.getEmail());
			
			int rows = 0;
			//execute the prepared statement
			rows = preparedStatement.executeUpdate();
			System.out.println(rows + " rows inserted");
			
			//Code to check if data is inserted
			ResultSet resultSet =  statement.executeQuery("select * from staff");
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1));
				
			}
			
		}
		//Catch primary key uniqueness violation errors
		catch (SQLIntegrityConstraintViolationException e) {
			// TODO: handle exception
			throw e;
		}
		//Catch all other exception
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//Fetch staff details using the id
	public Staff findStaff(String staffId) {
		// TODO Auto-generated method stub
		try {
			//Create a statement
			Statement statement = connection.createStatement();
			//Create query string
			String varname1 = "select * from staff where id = '"+staffId+"'";
			//Make a prepared statement using the query string
			PreparedStatement preparedStatement = connection.prepareStatement(varname1);
			//preparedStatement.setString(1, staffId);
			
			//Fetch data into result set
			ResultSet resultSet =  preparedStatement.executeQuery();
			
			//check if reslult set has more data.
			while(resultSet.next()) {
				
				//return by creating a new staff object
				return new Staff(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7), resultSet.getString(8), resultSet.getString(9));

			}
			
		} 
		//catch any sql exception if occurs
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//Update record by staff ID
	public void updateRecordById(Staff updateStaff) {
		// TODO Auto-generated method stub
		try {
			//create a connection
			Statement statement = connection.createStatement();
			//create a string to use with update query
			String varname1 = ""
					+ "UPDATE staff "
					+ "SET lastname = ?, firstname= ?, mi = ? , address = ?, city = ?, state = ?, telephone = ? "
					+ "WHERE id = ?";
			
			//create the prepared statement using the string
			PreparedStatement preparedStatement = connection.prepareStatement(varname1);
			
			//set prepared st. parameters
			preparedStatement.setString(1, updateStaff.getLastName());
			preparedStatement.setString(2, updateStaff.getLastName());
			preparedStatement.setString(3, updateStaff.getGender());
			preparedStatement.setString(4, updateStaff.getAddress());
			preparedStatement.setString(5, updateStaff.getCity());
			preparedStatement.setString(6, updateStaff.getState());
			preparedStatement.setString(7, updateStaff.getTelephone());
			preparedStatement.setString(8, updateStaff.getId());
			
			int rows = 0;
			//Execute update on the prepared st.
			rows = preparedStatement.executeUpdate();
			System.out.println(rows + " rows updated");
			
			//Validating result on console
			ResultSet resultSet =  statement.executeQuery("select * from staff");
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1));
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
