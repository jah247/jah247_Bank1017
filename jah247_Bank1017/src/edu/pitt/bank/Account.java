package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

/**
 * @author janhsu
 *
 */

/**
 * The Account class, which retrieve account data from the database
 *
 */
public class Account {
	private String accountID;
	private String type;
	private double balance;
	private double interestRate;
	private double penalty;
	private String status;
	private Date dateOpen;
	private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	private ArrayList<Customer> accountOwners = new ArrayList<Customer>();

	/**
	 * 
	 * @param accountID input
	 *            an account ID and built an account object
	 */
	public Account(String accountID) {
		String sql = "SELECT * FROM account ";
		sql += "WHERE accountID = '" + accountID + "'";
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while (rs.next()) {
				this.accountID = rs.getString("accountID");
				this.type = rs.getString("type");
				this.balance = rs.getDouble("balance");
				this.interestRate = rs.getDouble("interestRate");
				this.penalty = rs.getDouble("penalty");
				this.status = rs.getString("status");
				this.dateOpen = new Date();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage()); // Log error
			ErrorLogger.log(sql); // Log SELECT query

		}
		String sql2 = "SELECT * FROM transaction ";
		sql2 += "WHERE accountID = '" + accountID + "'";
		DbUtilities db2 = new MySqlUtilities();
		try {
			ResultSet rs2 = db2.getResultSet(sql2);
			while (rs2.next()) {
				String transactionType = rs2.getString("type");
				double amount = rs2.getDouble("amount");
				double stransactionBalance = rs2.getDouble("balance");
				createTransaction(accountID, transactionType, amount,
						stransactionBalance);
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
	 * @param accountType input
	 *            a type of the account
	 * @param initialBalance input
	 *            the initial value of the account
	 */
	public Account(String accountType, double initialBalance) {
		this.accountID = UUID.randomUUID().toString();
		this.type = accountType;
		this.balance = initialBalance;
		this.interestRate = 0;
		this.penalty = 0;
		this.status = "active";
		this.dateOpen = new Date();

		String sql = "INSERT INTO account ";
		sql += "(accountID,type,balance,interestRate,penalty,status,dateOpen) ";
		sql += " VALUES ";
		sql += "('" + this.accountID + "', ";
		sql += "'" + this.type + "', ";
		sql += this.balance + ", ";
		sql += this.interestRate + ", ";
		sql += this.penalty + ", ";
		sql += "'" + this.status + "', ";
		sql += "CURDATE());";
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
	 * return the account ID of a account object
	 */
	@Override
	public String toString() {
		return accountID;
	}

	/**
	 * 
	 * @param amount input
	 *            an amount you want to withdraw
	 */
	public void withdraw(double amount) {
		this.balance = this.balance - amount;
		System.out.println(balance);
		createTransaction(this.accountID, this.type, amount, this.balance);
		updateDatabaseAccountBalance();
	}

	/**
	 * 
	 * @param amount 
	 * input the amount you want to deposit
	 */
	public void deposit(double amount) {
		this.balance += amount;
		createTransaction(this.accountID, this.type, amount, this.balance);
		updateDatabaseAccountBalance();
	}

	/**
	 * update the balance of a target account
	 */
	private void updateDatabaseAccountBalance() {
		String sql = "UPDATE account SET balance = " + this.balance + " ";
		sql += "WHERE accountID = '" + this.accountID + "';";

		DbUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
	}

	/**
	 * 
	 * @param input
	 *            the transaction ID
	 * @return return a transaction object
	 */
	private Transaction createTransaction(String transactionID) {
		Transaction t = new Transaction(transactionID);
		transactionList.add(t);
		return t;
	}

	/**
	 * 
	 * @param input
	 *            the ID of an account
	 * @param input
	 *            the type of an account
	 * @param inupt
	 *            the transaction amount
	 * @param balance
	 *            the balance of the amount
	 * @return return a transaction object
	 */
	private Transaction createTransaction(String accountID, String type,
			double amount, double balance) {
		Transaction t = new Transaction(accountID, type, amount, balance);
		transactionList.add(t);
		return t;
	}

	/**
	 * 
	 * @param Customer
	 *            object of the account
	 */
	void addAccountOwner(Customer accountOwner) {
		accountOwners.add(accountOwner);
	}

	/**
	 * 
	 * @return ID of the account object
	 */
	public String getAccountID() {
		return this.accountID;
	}

	/**
	 * 
	 * @return balance of the account object
	 */
	public double getBalance() {
		return this.balance;
	}

	/**
	 * 
	 * @return type of the account object
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 
	 * @param type
	 *            input the account type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return interest of the account
	 */
	public double getInterestRate() {
		return this.interestRate;
	}

	/**
	 * 
	 * @param interestRate
	 *            set interest rate of the account
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * 
	 * @return penalty amount of the account
	 */
	public double getPanelty() {
		return this.penalty;
	}

	/**
	 * 
	 * @param penalty
	 *            set penalty of the account
	 */
	public void setPanelty(double penalty) {
		this.penalty = penalty;
	}

	/**
	 * 
	 * @return get the status of the amount
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 
	 * @param status
	 *            set status of an amount
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return Date of opening the account
	 */
	public Date getDateOpen() {
		return this.dateOpen;
	}

	/**
	 * 
	 * @param dateOpen
	 *            set the opening date of the account
	 */
	public void setDate(Date dateOpen) {
		this.dateOpen = dateOpen;
	}

	/**
	 * 
	 * @return get a transaction list
	 */
	public ArrayList getTransactionList() {
		return transactionList;
	}

	/**
	 * 
	 * @return get a account list
	 */
	public ArrayList getAccountOwners() {
		return accountOwners;
	}
}
