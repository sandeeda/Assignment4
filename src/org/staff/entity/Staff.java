package org.staff.entity;

public class Staff {

	String id;
	String lastName;
	String firstName;
	String gender;
	String address;
	String city;
	String state;
	String telephone;
	String email;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Staff() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Staff(String id, String lastName, String firstName, String gender, String address, String city, String state,
			String telephone, String email) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.gender = gender;
		this.address = address;
		this.city = city;
		this.state = state;
		this.telephone = telephone;
		this.email = email;
	}
	@Override
	public String toString() {
		return "Staff [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", gender=" + gender
				+ ", address=" + address + ", city=" + city + ", state=" + state + ", telephone=" + telephone
				+ ", email=" + email + "]";
	}
	
	
	
	
}
