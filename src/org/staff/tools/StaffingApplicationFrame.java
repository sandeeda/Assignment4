package org.staff.tools;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.humber.SQLInfo.OracleInfo;
import org.staff.dao.StaffToolsDao;
import org.staff.entity.Staff;

public class StaffingApplicationFrame extends JFrame {

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

	JPanel operationsPanel;
	JButton viewButton;
	JButton insertButton;
	JButton updateButton;
	JButton clearButton;
	JLabel connectivityLabel;
	
	
	ActionListener listener;
	StaffToolsDao staffToolsDao;

	class StaffToolActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == insertButton) {
				
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
			
			if(e.getSource() == viewButton) {
				Staff getStaff;
				
				getStaff = staffToolsDao.findStaff(IDTextField.getText());
				if(getStaff!=null) {
				lastNameTextField.setText(getStaff.getLastName());
				firstNameTextField.setText(getStaff.getFirstName());
				sexTextField.setText(getStaff.getGender());
				telephoneTextField.setText(getStaff.getTelephone());
				cityTextField.setText(getStaff.getCity());
				addressTextField.setText(getStaff.getAddress());
				stateTextField.setText(getStaff.getState());
				connectivityLabel.setText("Staff ID: "+IDTextField.getText()+" found");
				}
				else
					connectivityLabel.setText("No record found");
			}
			
			if(e.getSource() == updateButton) {
				Staff updateStaff = new Staff();
				updateStaff = staffToolsDao.findStaff(IDTextField.getText());
				updateStaff.setFirstName(firstNameTextField.getText());
				updateStaff.setLastName(lastNameTextField.getText());
				updateStaff.setGender(sexTextField.getText());
				updateStaff.setCity(cityTextField.getText());
				updateStaff.setAddress(addressTextField.getText());
				staffToolsDao.updateRecordById(updateStaff); 
				connectivityLabel.setText("1 row updated");
			}
			
			if(e.getSource() == clearButton) {
				firstNameTextField.setText("");
				lastNameTextField.setText("");
				IDTextField.setText("");
				cityTextField.setText("");
				sexTextField.setText("");
				telephoneTextField.setText("");
				stateTextField.setText("");
				if (staffToolsDao.createConnection()==true) {
					connectivityLabel.setText("DB Connected");
				}
			}

		}

	}

	public StaffingApplicationFrame() {
		listener = new StaffToolActionListener();
		staffToolsDao = new StaffToolsDao();
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
		setSize(500,300);
		setTitle("Staff DB tool");
		Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\sdsts\\Documents\\workspace-spring-tool-suite-4-4.13.0.RELEASE\\Assignment04N01472825\\icon\\db.png");   
		setIconImage(icon);
		add(operationsPanel, BorderLayout.SOUTH);
		add(staffInformationPanel, BorderLayout.NORTH);
	}

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

	private void createStaffInformationPanel() {
		// TODO Auto-generated method stub

		staffInformationPanel = new JPanel();
		staffInformationPanel.setBorder(new TitledBorder(new EtchedBorder(), "Staffing Information"));
		staffInformationPanel.setLayout(new GridLayout(8,2));

		IDLabel = new JLabel("ID");
		IDTextField = new JTextField(20);

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
