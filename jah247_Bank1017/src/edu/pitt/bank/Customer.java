package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

public class Customer {
	private String customerID;
	private String firstName;
	private String lastName;
	private String ssn;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String loginName;
	private String pin;

	/**
	 * 
	 * @param customerID
	 *            input the customer ID
	 */
	public Customer(String customerID) {
		String sql = "SELECT * FROM customer WHERE customerID = '" + customerID
				+ "';";

		DbUtilities db = new MySqlUtilities();

		try {
			ResultSet rs = db.getResultSet(sql);
			while (rs.next()) {
				this.customerID = rs.getString("customerID");
				this.firstName = rs.getString("firstName");
				this.lastName = rs.getString("lastName");
				this.ssn = rs.getString("ssn");
				this.streetAddress = rs.getString("streetAddress");
				this.state = rs.getString("state");
				this.city = rs.getString("city");
				this.zip = rs.getString("zip");
				this.loginName = rs.getString("loginName");
				this.pin = rs.getString("pin");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage()); // Log error
			ErrorLogger.log(sql); // Log SELECT query
		}
	}

	/**
	 * 
	 * @param lastName
	 *            customer's last name
	 * @param firstName
	 *            customer's first name
	 * @param ssn
	 *            customer's ssn
	 * @param loginName
	 *            customer's login name
	 * @param pin
	 *            customer's pin number
	 * @param streetAddress
	 *            customer's street address
	 * @param city
	 *            customer's living city
	 * @param state
	 *            customer's living state
	 * @param zip
	 *            custimer's ZIP
	 */
	public Customer(String lastName, String firstName, String ssn,
			String loginName, int pin, String streetAddress, String city,
			String state, int zip) {
		this.customerID = UUID.randomUUID().toString();
		this.firstName = firstName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.loginName = loginName;
		this.pin = Integer.toString(pin);
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zip = Integer.toString(zip);
		String sql = "INSERT INTO customer";
		sql += "(customerID,lastName,firstName,ssn,loginName,pin,streetAddress,city,state,zip) ";
		sql += " VALUES ";
		sql += "('" + this.customerID + "', ";
		sql += "'" + this.lastName + "', ";
		sql += "'" + this.firstName + "', ";
		sql += "'" + this.ssn + "', ";
		sql += "'" + this.pin + "', ";
		sql += "'" + this.streetAddress + "', ";
		sql += "'" + this.city + "', ";
		sql += "'" + this.state + "', ";
		sql += "'" + this.zip + "'); ";
		try {
			DbUtilities db = new MySqlUtilities();
			db.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorLogger.log(e.getMessage()); // Log error
			ErrorLogger.log(sql); // Log SELECT query
		}
	}

	/**
	 * 
	 * @return customer's ID
	 */
	public String getCustomerID() {
		return this.customerID;
	}

	/**
	 * 
	 * @return get customer's first name
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * 
	 * @return get customer's last name
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * 
	 * @return customer's get SSN
	 */
	public String getSsn() {
		return this.ssn;
	}

	/**
	 * 
	 * @return get customer's street address
	 */
	public String getStreetAddress() {
		return this.streetAddress;
	}

	/**
	 * 
	 * @param streetAddress
	 *            set customer's street address
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * 
	 * @return get customer's state name
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * 
	 * @param state
	 *            set customer's state name
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 
	 * @return get customer's ZIP
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * 
	 * @param zip
	 *            set customer's ZIP
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * 
	 * @return get the log-in name
	 */
	public String getLoginName() {
		return this.loginName;
	}

	/**
	 * 
	 * @return get the pin number
	 */
	public String getPin() {
		return this.pin;
	}
}
