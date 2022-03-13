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
	
	private Connection connection;
	public StaffToolsDao() {
		// TODO Auto-generated constructor stub
		createConnection();
	}
	public boolean createConnection() {
		// TODO Auto-generated method stub
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
				Class.forName(OracleInfo.DRIVER_CLASS_MYSQL);
				connection=DriverManager.getConnection(  
						OracleInfo.URL,OracleInfo.userName,OracleInfo.pwd);  
				System.out.println("Connection created");
				return true;
				
			} catch (ClassNotFoundException classNotFoundException) {
				// TODO: handle exception
				classNotFoundException.printStackTrace();
				return false;
			} catch (SQLException sqlException) {
				// TODO: handle exception
				sqlException.printStackTrace();
				return false;
			}
			
		
	}
	public void insertRecord(Staff staff) throws SQLIntegrityConstraintViolationException {
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
			
			
			ResultSet resultSet =  statement.executeQuery("select * from staff");
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1));
				
			}
			
		} catch (SQLIntegrityConstraintViolationException e) {
			// TODO: handle exception
			throw e;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public Staff findStaff(String staffId) {
		// TODO Auto-generated method stub
		System.out.println(staffId);
		try {
			Statement statement = connection.createStatement();
			String varname1 = "select * from staff where id = '"+staffId+"'";
			PreparedStatement preparedStatement = connection.prepareStatement(varname1);
			//preparedStatement.setString(1, staffId);
			
			ResultSet resultSet =  preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				//System.out.println(resultSet.getString(1)+" "+resultSet.getString(2));
				return new Staff(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7), resultSet.getString(8), resultSet.getString(9));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void updateRecordById(Staff updateStaff) {
		// TODO Auto-generated method stub
		try {
			Statement statement = connection.createStatement();
			String varname1 = ""
					+ "UPDATE staff "
					+ "SET lastname = ?, firstname= ?, mi = ? , address = ?, city = ?, state = ?, telephone = ? "
					+ "WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(varname1);
			preparedStatement.setString(1, updateStaff.getLastName());
			preparedStatement.setString(2, updateStaff.getLastName());
			preparedStatement.setString(3, updateStaff.getGender());
			preparedStatement.setString(4, updateStaff.getAddress());
			preparedStatement.setString(5, updateStaff.getCity());
			preparedStatement.setString(6, updateStaff.getState());
			preparedStatement.setString(7, updateStaff.getTelephone());
			preparedStatement.setString(8, updateStaff.getId());
			
			int rows = 0;
			rows = preparedStatement.executeUpdate();
			System.out.println(rows + " rows updated");
			
			
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
