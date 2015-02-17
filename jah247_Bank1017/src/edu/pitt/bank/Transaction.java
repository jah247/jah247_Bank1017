package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

public class Transaction {
	private String transactionID;
	private String accountID;
	private String type;
	private double amount;
	private double balance;
	private Date transactionDate;

	/**
	 * 
	 * @param transactionID
	 *            the transaction ID
	 */
	public Transaction(String transactionID) {
		String sql = "SELECT * FROM transaction ";
		sql += "WHERE transactionID = '" + transactionID + "'";
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while (rs.next()) {
				this.transactionID = rs.getString("transactionID");
				this.accountID = rs.getString("accountID");
				this.type = rs.getString("type");
				this.amount = rs.getDouble("amount");
				this.balance = rs.getDouble("balance");
				this.transactionDate = new Date();
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
	 * @param accountID
	 *            account ID of the customer
	 * @param type
	 *            account type of the customer
	 * @param amount
	 *            transaction amount
	 * @param balance
	 *            account balance
	 */
	public Transaction(String accountID, String type, double amount,
			double balance) {
		this.transactionID = UUID.randomUUID().toString();
		this.type = type;
		this.amount = amount;
		this.accountID = accountID;
		this.balance = balance;

		String sql = "INSERT INTO transaction ";
		sql += "(transactionID, accountID, amount, transactionDate, type, balance) ";
		sql += " VALUES ";
		sql += "('" + this.transactionID + "', ";
		sql += "'" + this.accountID + "', ";
		sql += amount + ", ";
		sql += "CURDATE(), ";
		sql += "'" + this.type + "', ";
		sql += balance + ");";

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
	 * @return get transaction ID
	 */
	public String getTransactionID() {
		return transactionID;
	}

	/**
	 * 
	 * @return get customer's account ID
	 */
	public String getAccountID() {
		return accountID;
	}

	/**
	 * 
	 * @return get customer's account type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 *            set customer's account type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return get the transaction amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * 
	 * @return get balance of the account
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * 
	 * @return return the transaction day
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

}
