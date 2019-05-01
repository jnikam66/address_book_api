package org.maven.rest.Entity;

/**
 * @author jyotinikam
 * 
 * Data Model for Contacts
 *
 */
public class Contact {
	String name;
	String contactNumber;
	String contactEmail;
	String address;
	
	public Contact() {
		
	}
	
	public Contact(String name, String contactNumber, String contactEmail, String address) {
		super();
		this.name = name;
		this.contactNumber = contactNumber;
		this.contactEmail = contactEmail;
		this.address = address;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
