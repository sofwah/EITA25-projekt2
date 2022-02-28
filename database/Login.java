package database;

	import java.awt.BorderLayout; 
	import java.awt.EventQueue;

	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.border.EmptyBorder;

	import javax.swing.JTextField;
	import javax.swing.JPasswordField;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JButton;
	import java.awt.event.ActionListener;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.awt.event.ActionEvent;
	
	
public class Login extends JFrame {
	//testa ladda upp kod
		private Connection connection;
		
		private final String url = "jdbc:mysql://localhost:3306/hospitalsystem";
		private final String user = "root";
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
						String pass = "grupp13";
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
		 */
		public Login() {
			this.pass=pass;
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			staffName = new JTextField();
			staffName.setBounds(195, 85, 180, 20);
			contentPane.add(staffName);
			staffName.setColumns(10);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(195, 160, 180, 20);
			contentPane.add(passwordField);
			
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
						
						String name = staffName.getText();
						String password = passwordField.getText();
						String query = "SELECT * FROM login WHERE name=? AND password=? ";
						PreparedStatement statement = connection.prepareStatement(query);
						statement.setString(1, name);
						statement.setString(2, password);
						
						ResultSet set =statement.executeQuery();
						if (set.next()) {
							
						//	StaffUITest staffFrame =  new StaffUITest();
						//	staffFrame.setVisible(true);
							if(ae.getSource() == btnLogin) {
								dispose();
							}
							System.out.println("Connection is established.");
						}
						else {
							JOptionPane.showMessageDialog(null, "login fail");
						}
						
						

					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					

				}
			});
			btnLogin.setBounds(145, 213, 89, 23);
			contentPane.add(btnLogin);
		}
		
	
	
}
