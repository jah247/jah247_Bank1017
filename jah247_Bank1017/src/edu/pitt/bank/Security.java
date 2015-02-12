package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

public class Security {
	private String userID;
	private ArrayList<String> groups = new ArrayList<>();

	/**
	 * 
	 * @param loginName
	 *            customer's log-in name
	 * @param pin
	 *            customer's pin number
	 * @return an customer object
	 */
	public Customer validateLogin(String loginName, int pin) {
		Customer customer = null;
		String sql = "SELECT * FROM customer WHERE loginName = '" + loginName
				+ "' AND pin = '" + pin + "';";
		System.out.println(sql);
		DbUtilities db = new MySqlUtilities();
		try {

			ResultSet rs = db.getResultSet(sql);
			if (rs.next()) {
				userID = rs.getString("customerID");
				customer = new Customer(rs.getString("customerID"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage()); // Log error
			ErrorLogger.log(sql); // Log SELECT query
		}

		return customer;

	}

	/**
	 * 
	 * @return groups that the customer belongs to
	 */
	ArrayList<String> listUserGroups() {
		String sql = "SELECT groupName FROM groups JOIN user_permissions on user_permissions.groupID = groups.groupID JOIN customer ON customerId = groupOrUserID WHERE customerID ='"
				+ userID + "';";
		System.out.println(sql);
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			if (rs.next()) {
				groups.add(rs.getString("groupName"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage()); // Log error
			ErrorLogger.log(sql); // Log SELECT query
		}

		return groups;

	}
}
