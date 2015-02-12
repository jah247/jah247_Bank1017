package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

/**
 * 
 * @author janhsu
 *
 */
public class Bank {
	private ArrayList<Account> accountList = new ArrayList<Account>();
	private ArrayList<Customer> customerList = new ArrayList<Customer>();

	/**
	 * Bank constructor which can load the account data, create both account and
	 * customer objects
	 */
	Bank() {
		loadAccount();
		setAccountOwners();
	}

	/**
	 * Load account data, create account object and add to account list
	 */
	void loadAccount() {
		String sql = "SELECT * FROM account ";
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while (rs.next()) {
				String accountID = rs.getString("accountID");
				Account account = new Account(accountID);
				this.accountList.add(account);
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
	 * @param acaountID
	 *            input ID of the account
	 * @return the target account object
	 */
	Account findAccount(String accountID) {
		Account targetAccount = null;
		for (int i = 0; i < accountList.size(); i++) {

			if (accountList.get(i).getAccountID().equals(accountID)) {
				targetAccount = (Account) accountList.get(i);
			}
		}
		return targetAccount;
	}

	/**
	 * create customer list
	 */
	void setAccountOwners() {

		String sql = "SELECT * FROM account JOIN customer_account ON accountId = fk_accountId";
		sql += "JOIN customer ON fk_customerId = customerId;";
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while (rs.next()) {
				String customerID = rs.getString("customerID");
				String accountID = rs.getString("accountID");
				Account account = new Account(accountID);
				Customer customer = new Customer(customerID);
				this.customerList.add(customer);
				account.addAccountOwner(customer);
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
	 * @param customerID
	 *            input the customer ID
	 * @return the target customer object
	 */
	Customer findCustomer(String customerID) {
		Customer targetCustomer = null;
		for (int i = 0; i < customerList.size(); i++) {

			if (customerList.get(i).getCustomerID().equals(customerID)) {
				targetCustomer = (Customer) customerList.get(i);
			}
		}
		return targetCustomer;
	}
}
