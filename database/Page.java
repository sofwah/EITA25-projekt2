package src.database;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Page extends JFrame{
	private Connection connection;
	private final String url = "jdbc:postgresql://pgserver.mah.se/festivaltest";
	private final String user = "af8396";
	private String pass;

	private JPanel contentPane;
	private JTextField staffName;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String pass = "jason";
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @return 
	 */
	public void page() {
		this.pass=pass;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton writeto = new JButton(); 
		writeto.setBounds(195, 85, 180, 20);
		contentPane.add(writeto);
		
		
		//staffName.setBounds(195, 85, 180, 20);
		//staffName = new JTextField();
		//contentPane.add(staffName);
		//staffName.setColumns(10);
		
		//passwordField = new JPasswordField();
		//passwordField.setBounds(195, 160, 180, 20);
		//contentPane.add(passwordField);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(37, 163, 104, 14);
		contentPane.add(lblPassword);
		
		JLabel lblStaff = new JLabel("Name");
		lblStaff.setBounds(37, 88, 46, 14);
		contentPane.add(lblStaff);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					Class.forName("org.postgresql.Driver").newInstance();
					connection = DriverManager.getConnection(url, user, pass);
					
					//String name = staffName.getText();
				//	String password = passwordField.getText();
				//	String query = "SELECT * FROM login WHERE name=? AND password=? ";
				//	PreparedStatement statement = connection.prepareStatement(query);
				//	statement.setString(1, name);
				//	statement.setString(2, password);
					
					//ResultSet set =statement.executeQuery();
				//	if (set.next()) {
						
					//	StaffUITest staffFrame =  new StaffUITest();
					//	staffFrame.setVisible(true);
						if(ae.getSource() == btnLogin) {
							dispose();
						}
						System.out.println("Connection is established.");
					//}
					//else {
						JOptionPane.showMessageDialog(null, "login fail");
					//}
					
					

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				

			}
		});
		btnLogin.setBounds(145, 213, 89, 23);
		contentPane.add(btnLogin);
	}

}
