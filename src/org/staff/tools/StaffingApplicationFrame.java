/*************************************************************************************************
*  Course_Name – Assignment x                                                                                                                                *

*  I declare that this assignment is my own work in accordance with Humber Academic Policy.        *

*  No part of this assignment has been copied manually or electronically from any other source       *

*  (including web sites) or distributed to other students/social media.                                                       *
                                                                                                                                                                             
*  Name: Sandeep Das Student ID: N01472825 Date: 13th Mar 2022  		          
*/
package org.staff.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.staff.dao.StaffToolsDao;
import org.staff.entity.Staff;

public class StaffingApplicationFrame extends JFrame {

	//Creating the panel and the components for input fields
	JPanel staffInformationPanel;
	JLabel IDLabel;
	JTextField IDTextField;

	JLabel lastNameLabel;
	JTextField lastNameTextField;

	JLabel firstNameLabel;
	JTextField firstNameTextField;

	JLabel sexLabel;
	JTextField sexTextField;

	JLabel addressLabel;
	JTextField addressTextField;

	JLabel cityLabel;
	JTextField cityTextField;

	JLabel stateLabel;
	JTextField stateTextField;

	JLabel telephoneLabel;
	JTextField telephoneTextField;

	
	//Create to operations panel and its buttons
	JPanel operationsPanel;
	JButton viewButton;
	JButton insertButton;
	JButton updateButton;
	JButton clearButton;
	JLabel connectivityLabel;
	
	//Create the listener
	ActionListener listener;
	//Create the object for accessing methods of DAO implementations
	StaffToolsDao staffToolsDao;

	
	//inner class implementing action listener
	class StaffToolActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			//Event handling defined for insert button
			if (e.getSource() == insertButton) {
				//validate data if the data is okay
				if(validateData()) {
					//if data is valid, read the data from fields, create a staff object and use the DAO tools for inserting the data
					Staff insertStaff = new Staff(IDTextField.getText(), lastNameTextField.getText(), firstNameTextField.getText(), sexTextField.getText(), addressTextField.getText(), cityTextField.getText(), stateTextField.getText(), telephoneTextField.getText(), IDTextField.getText()+"@humber.ca");
					try {
						staffToolsDao.insertRecord(insertStaff);
						connectivityLabel.setText("1 row updated");
					} catch (SQLIntegrityConstraintViolationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						connectivityLabel.setText("Duplicate primary key!");
					}
				}
				else {
					//If data is invalid, show error message to user
					JOptionPane.showMessageDialog(null, "Invalid data");
				}
				
				
			}
			
			//Handle event for view button
			if(e.getSource() == viewButton) {
				Staff getStaff;
				
				//Check if the ID exists n DB using the findStaff method
				getStaff = staffToolsDao.findStaff(IDTextField.getText());
				//check if the getStaff object is populated
				if(getStaff!=null) {
				lastNameTextField.setText(getStaff.getLastName());
				firstNameTextField.setText(getStaff.getFirstName());
				sexTextField.setText(getStaff.getGender());
				telephoneTextField.setText(getStaff.getTelephone());
				cityTextField.setText(getStaff.getCity());
				addressTextField.setText(getStaff.getAddress());
				stateTextField.setText(getStaff.getState());
				//Update the connectivity Label
				connectivityLabel.setText("Staff ID: "+IDTextField.getText()+" found");
				}
				else
					connectivityLabel.setText("No record found");
			}
			
			//Check if update button was pressed
			if(e.getSource() == updateButton) {
				Staff updateStaff = new Staff();
				//Populate a staff object with the text field values and call the DAO method to update using ID
				updateStaff = staffToolsDao.findStaff(IDTextField.getText());
				updateStaff.setFirstName(firstNameTextField.getText());
				updateStaff.setLastName(lastNameTextField.getText());
				updateStaff.setGender(sexTextField.getText());
				updateStaff.setCity(cityTextField.getText());
				updateStaff.setAddress(addressTextField.getText());
				staffToolsDao.updateRecordById(updateStaff); 
				connectivityLabel.setText("1 row updated");
			}
			//Check if clear button was pressed
			if(e.getSource() == clearButton) {
				
				//Clear text fields and set them to blank
				firstNameTextField.setText("");
				lastNameTextField.setText("");
				IDTextField.setText("");
				cityTextField.setText("");
				addressTextField.setText("");
				sexTextField.setText("");
				telephoneTextField.setText("");
				stateTextField.setText("");
				if (staffToolsDao.createConnection()==true) {
					connectivityLabel.setText("DB Connected");
				}
			}

		}

		private boolean validateData() {
			// TODO Auto-generated method stub
			//Simple if nests to validate length of data and not null parameters
			if((IDTextField.getText().length()<10 && IDTextField.getText().length()>0 && IDTextField.getText()!=null)
					&& (lastNameTextField.getText().length()<16 && lastNameTextField.getText().length()>0 && lastNameTextField.getText()!=null)
					&& (firstNameTextField.getText().length()<16 && firstNameTextField.getText().length()>0 && firstNameTextField.getText()!=null)
					&& (sexTextField.getText().equalsIgnoreCase("m") || sexTextField .getText().equalsIgnoreCase("f"))
					&& (addressTextField.getText().length()<=20 && addressTextField.getText().length()>0 && addressTextField.getText()!=null) 
					&& (cityTextField.getText().length()<=20 && cityTextField.getText().length()>0 && cityTextField.getText()!=null)
					&& (stateTextField.getText().length()==2 && stateTextField.getText()!=null)
					&& (telephoneTextField.getText().length()==10))
			{
				return true;
			}
			return false;
		}

	}

	//Constructor
	public StaffingApplicationFrame() {
		//Creating listener object
		listener = new StaffToolActionListener();
		
		//creating StaffToolsDao object
		staffToolsDao = new StaffToolsDao();
		
		//create panels and pack them
		createStaffInformationPanel();
		createOperationsPanel();
		bundleLayout();
		if (staffToolsDao.createConnection()==true) {
			connectivityLabel.setText("DB Connected");
		}
	}

	/*
	 * private void connectDB() { // TODO Auto-generated method stub Connection con
	 * = null; try { Class.forName(OracleInfo.DRIVER_CLASS_MYSQL);
	 * con=DriverManager.getConnection(
	 * OracleInfo.URL,OracleInfo.userName,OracleInfo.pwd);
	 * 
	 * } catch (ClassNotFoundException classNotFoundException) { // TODO: handle
	 * exception classNotFoundException.printStackTrace(); } catch (SQLException
	 * sqlException) { // TODO: handle exception sqlException.printStackTrace();
	 * }finally { try { if (con != null) con.close(); } catch (SQLException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } } }
	 */
	private void bundleLayout() {
		// TODO Auto-generated method stub
		
		//Pack the layout , add an icon
		setSize(500,300);
		setTitle("Staff DB tool");
		Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\sdsts\\Documents\\workspace-spring-tool-suite-4-4.13.0.RELEASE\\Assignment04N01472825\\icon\\db.png");   
		setIconImage(icon);
		add(operationsPanel, BorderLayout.SOUTH);
		add(staffInformationPanel, BorderLayout.NORTH);
	}

	//create the operations panel
	private void createOperationsPanel() {
		// TODO Auto-generated method stub
		operationsPanel = new JPanel();
		operationsPanel.setLayout(new GridLayout(2,1));
		
		JPanel wrapperPanel = new JPanel();
		
		viewButton = new JButton("View");
		viewButton.addActionListener(listener);
		
		insertButton = new JButton("Insert");
		insertButton.addActionListener(listener);
		
		updateButton = new JButton("Update");
		updateButton.addActionListener(listener);
		
		clearButton = new JButton("Clear");
		clearButton.addActionListener(listener);
		
		connectivityLabel = new JLabel("");
		
		wrapperPanel.add(viewButton);
		wrapperPanel.add(insertButton);
		wrapperPanel.add(updateButton);
		wrapperPanel.add(clearButton);
		operationsPanel.add(wrapperPanel);
		operationsPanel.add(connectivityLabel);

	}

	//create the info input panel
	private void createStaffInformationPanel() {
		// TODO Auto-generated method stub

		staffInformationPanel = new JPanel();
		staffInformationPanel.setBorder(new TitledBorder(new EtchedBorder(), "Staffing Information"));
		staffInformationPanel.setLayout(new GridLayout(8,2));

		IDLabel = new JLabel("ID");
		IDTextField = new JTextField(20);
		IDTextField.setBackground(Color.YELLOW);

		lastNameLabel = new JLabel("Last Name");
		lastNameTextField = new JTextField(20);

		firstNameLabel = new JLabel("First Name");
		firstNameTextField = new JTextField(20);
		
		sexLabel = new JLabel("Gender");
		sexTextField = new JTextField(20);

		addressLabel = new JLabel("Address");
		addressTextField = new JTextField(20);

		cityLabel = new JLabel("City");
		cityTextField = new JTextField(20);
		
		stateLabel = new JLabel("State");
		stateTextField = new JTextField(20);
		
		telephoneLabel = new JLabel("Telephone");
		telephoneTextField = new JTextField(20);
		
		
		staffInformationPanel.add(IDLabel);
		staffInformationPanel.add(IDTextField);
		staffInformationPanel.add(lastNameLabel);
		staffInformationPanel.add(lastNameTextField);
		staffInformationPanel.add(firstNameLabel);
		staffInformationPanel.add(firstNameTextField);
		staffInformationPanel.add(sexLabel);
		staffInformationPanel.add(sexTextField);
		staffInformationPanel.add(addressLabel);
		staffInformationPanel.add(addressTextField);
		staffInformationPanel.add(cityLabel);
		staffInformationPanel.add(cityTextField);
		staffInformationPanel.add(stateLabel);
		staffInformationPanel.add(stateTextField);
		staffInformationPanel.add(telephoneLabel);
		staffInformationPanel.add(telephoneTextField);

	}

}
