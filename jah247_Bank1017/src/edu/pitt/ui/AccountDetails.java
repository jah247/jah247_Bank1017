package edu.pitt.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import edu.pitt.bank.Account;
import edu.pitt.bank.Customer;
import edu.pitt.bank.Security;
import edu.pitt.bank.Transaction;
import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.MySqlUtilities;
import edu.pitt.utilities.ErrorLogger;

public class AccountDetails {
	private DbUtilities db;
	private JFrame frame;
	private JTextField textField;
	private JTextField txtXxxWelcomeTo;
	private Customer accountOwner;
	private ArrayList<Account> accountList = new ArrayList<>();
	private ArrayList<String> accountIDList = new ArrayList<>();
	private Account selectedAccount;
	private JComboBox cboAccount;
	private String customerName;
	private String type;
	private double balance;
	private double interestRate;
	private double penalty;
	private String status;
	private JLabel lblAccountType;
	private JLabel lblBalance;
	private JLabel lblInterestRate;
	private JLabel lblPenalty;
	private Timer time;

	/**
	 * Create the application.
	 * @param c a customer object
	 */
	public AccountDetails(Customer c) {
		accountOwner = c;
		customerName = c.getFirstName() + " " + c.getLastName();
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {


		String sql = "SELECT * FROM customer JOIN customer_account ON customerId = fk_customerId JOIN account ON fk_accountId = accountId WHERE customerId = '"
				+ accountOwner.getCustomerID() + "';";
		db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while (rs.next()) {
				accountIDList.add(rs.getString("accountID"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}
		for (String item : accountIDList) {
			accountList.add(new Account(item));
		}
		// System.out.println(accountOwner.getCustomerID());
		class SetTimer implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null,
						"Time Out! You have to log-in again!");
				frame.dispatchEvent(new WindowEvent(frame,
						WindowEvent.WINDOW_CLOSING));
				db.closeDbConnection();
			}
		}
		SetTimer st = new SetTimer();
		time = new Timer(60 * 1000, st);
		time.start();
		frame = new JFrame();
		frame.setBounds(100, 100, 520, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblYourAccount = new JLabel("Your account: ");
		lblYourAccount.setBounds(23, 64, 96, 16);
		frame.getContentPane().add(lblYourAccount);

		cboAccount = new JComboBox();
		cboAccount.setBounds(141, 60, 277, 27);
		frame.getContentPane().add(cboAccount);
		for (Account a : accountList) {
			cboAccount.addItem(a);
		}
		ActionListener selectAccount = new SelectAccount();
		cboAccount.addActionListener((ActionListener) selectAccount);
		lblAccountType = new JLabel("Account type: " + type);
		lblAccountType.setBounds(23, 112, 160, 16);
		frame.getContentPane().add(lblAccountType);

		lblBalance = new JLabel("Balance: " + balance);
		lblBalance.setBounds(23, 127, 160, 16);
		frame.getContentPane().add(lblBalance);

		lblInterestRate = new JLabel("Interest Rate: " + interestRate);
		lblInterestRate.setBounds(23, 142, 160, 16);
		frame.getContentPane().add(lblInterestRate);

		lblPenalty = new JLabel("Penalty: " + penalty);
		lblPenalty.setBounds(23, 157, 160, 16);
		frame.getContentPane().add(lblPenalty);

		JLabel lblAmount = new JLabel("Amount:");
		lblAmount.setBounds(232, 127, 62, 16);
		frame.getContentPane().add(lblAmount);

		textField = new JTextField();
		textField.setBounds(306, 121, 111, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.setBounds(219, 152, 107, 29);
		frame.getContentPane().add(btnDeposit);
		Depo depo = new Depo();
		btnDeposit.addActionListener(depo);

		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setBounds(327, 152, 96, 29);
		frame.getContentPane().add(btnWithdraw);
		Widr widr = new Widr();
		btnWithdraw.addActionListener(widr);

		JButton btnShowTransactions = new JButton("Show Transactions");
		btnShowTransactions.setBounds(134, 205, 192, 29);
		frame.getContentPane().add(btnShowTransactions);

		btnShowTransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Account selectedAccount = (Account) cboAccount
						.getSelectedItem();
				TransactionUI tran = new TransactionUI(selectedAccount);
			}

		});

		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(327, 205, 96, 29);
		frame.getContentPane().add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispatchEvent(new WindowEvent(frame,
						WindowEvent.WINDOW_CLOSING));
				db.closeDbConnection();
			}

		});
		JLabel lblWelcome = new JLabel(customerName
				+ ", welcome to 1017 bank. You have the following ");
		lblWelcome.setBounds(23, 19, 500, 16);
		frame.getContentPane().add(lblWelcome);

		JLabel lblWelcome2 = new JLabel(
				"permissions in this system: Administrator, Branch Manager, Customer");
		lblWelcome2.setBounds(23, 36, 500, 16);
		frame.getContentPane().add(lblWelcome2);

	}

	/**
	 * 
	 * @param str
	 *            input a String
	 * @return true if it is a number false if it is not
	 */
	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	class SelectAccount implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			selectedAccount = (Account) cboAccount.getSelectedItem();
			type = selectedAccount.getType();
			balance = selectedAccount.getBalance();
			interestRate = selectedAccount.getInterestRate();
			penalty = selectedAccount.getPanelty();
			status = selectedAccount.getStatus();
			lblAccountType.setText(("Account type: " + type));
			lblBalance.setText("Balance: " + balance);
			lblInterestRate.setText("Interest Rate: " + interestRate);
			lblPenalty.setText("Penalty: " + penalty);
		}
	}

	/**
	 * implement deposit function
	 *
	 */
	class Depo implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (selectedAccount == null) {
				JOptionPane.showMessageDialog(null,
						"Please, select an account!");
			} else {
				if (isNumeric(textField.getText())) {
					double depoAmount = Double.parseDouble(textField.getText());
					selectedAccount.deposit(depoAmount);

				} else {
					JOptionPane.showMessageDialog(null, "Invalid Value!");
				}
			}
		}
	}

	/**
	 * implement withdrawal function
	 *
	 */
	class Widr implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (selectedAccount == null) {
				JOptionPane.showMessageDialog(null,
						"Please, select an account!");
			} else {
				if (isNumeric(textField.getText())) {
					double widrAmount = Double.parseDouble(textField.getText());
					selectedAccount.withdraw(widrAmount);

				} else {
					JOptionPane.showMessageDialog(null, "Invalid Value!");
				}
			}
		}
	}
}
