package edu.pitt.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import edu.pitt.bank.Customer;
import edu.pitt.bank.Security;

public class LoginUI {

	private JFrame frame;
	private JTextField txtLogin;
	private JTextField txtPassword;
	private Customer c;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI window = new LoginUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblLoginName = new JLabel("Login Name: ");
		lblLoginName.setBounds(32, 80, 101, 22);
		frame.getContentPane().add(lblLoginName);

		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setBounds(32, 125, 76, 22);
		frame.getContentPane().add(lblPassword);

		txtLogin = new JTextField();
		txtLogin.setBounds(125, 77, 262, 28);
		frame.getContentPane().add(txtLogin);
		txtLogin.setColumns(10);

		txtPassword = new JTextField();
		txtPassword.setBounds(125, 125, 262, 28);
		frame.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(231, 182, 76, 21);
		frame.getContentPane().add(btnLogin);

		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(333, 180, 54, 25);
		frame.getContentPane().add(btnExit);

		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Thanks for coming, see you next time!");
				frame.dispatchEvent(new WindowEvent(frame,
						WindowEvent.WINDOW_CLOSING));

			}
		});

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String loginName = txtLogin.getText();
				String pin = txtPassword.getText();
				if (!pin.equals("") && !loginName.equals("")) {
					if (isInteger(pin)) {
						int pin2 = Integer.parseInt((txtPassword.getText()));
						Security s = new Security();
						c = s.validateLogin(loginName, pin2);
						if (c != null) {
							AccountDetails ad = new AccountDetails(c);
							frame.setVisible(false);
						} else {
							JOptionPane.showMessageDialog(null,
									"Invalid Message!");
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"Pin number or Login name is wrong!");
					}
				} else {

				}
			}

		});

	}

	/**
	 * 
	 * @param s
	 *            input a String
	 * @return return true if it is a integer otherwise return false
	 */
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
