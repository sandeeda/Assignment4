package org.staff.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.humber.SQLInfo.OracleInfo;
import org.staff.entity.Staff;

public class StaffToolsDao {
	
	private Connection connection;
	public StaffToolsDao() {
		// TODO Auto-generated constructor stub
		System.out.println("Here");
		createConnection();
	}
	private void createConnection() {
		// TODO Auto-generated method stub
		if(connection!=null)
		{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
			
			try {
				Class.forName(OracleInfo.DRIVER_CLASS_MYSQL);
				connection=DriverManager.getConnection(  
						OracleInfo.URL,OracleInfo.userName,OracleInfo.pwd);  
				System.out.println("Connection created");;
				
			} catch (ClassNotFoundException classNotFoundException) {
				// TODO: handle exception
				classNotFoundException.printStackTrace();
			} catch (SQLException sqlException) {
				// TODO: handle exception
				sqlException.printStackTrace();
			}
		
	}
	public void insertRecord(Staff staff) {
		try {
			Statement statement = connection.createStatement();
			String varname1 = ""
					+ "insert into staff (id, lastname, firstname, mi, address, city, state, telephone, email) "
					+ "values "
					+ "(?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(varname1);
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
			rows = preparedStatement.executeUpdate();
			System.out.println(rows + " rows inserted");
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
