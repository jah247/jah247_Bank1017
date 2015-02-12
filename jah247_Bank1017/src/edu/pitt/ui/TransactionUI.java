package edu.pitt.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.pitt.bank.Account;
import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

public class TransactionUI {

	private JFrame frame;
	private JScrollPane transactionPane;
	private JTable tblTransactions;
	private Account selectedAccount;

	/**
	 * Create the application.
	 * @param selectedAccount a selected account object
	 */
	public TransactionUI(Account selectedAccount) {
		this.selectedAccount = selectedAccount;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Account Transaction");
		frame.setBounds(100, 100, 700, 300);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		transactionPane = new JScrollPane();
		frame.getContentPane().add(transactionPane);
		DbUtilities db = new MySqlUtilities();
		String[] cols = { "Account ID", "Amount", "Date", "Type", "Balance" };
		String sql = "SELECT accountID, amount , transactionDate, type, balance FROM transaction WHERE accountID = '"
				+ this.selectedAccount.getAccountID() + "';";
		try {
			DefaultTableModel transactionList = db.getDataTable(sql, cols);
			tblTransactions = new JTable(transactionList);
			tblTransactions.setFillsViewportHeight(true);
			tblTransactions.setShowGrid(true);
			tblTransactions.setGridColor(Color.black);
			transactionPane.getViewport().add(tblTransactions);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage()); // Log error
			ErrorLogger.log(sql); // Log SELECT query
		}
	}
}
