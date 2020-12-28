package InvetoRestProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import InvetoRestProject.classes.Manager;
import InvetoRestProject.classes.User;
import InvetoRestProject.classes.Worker;

import java.awt.Font;
import java.awt.Window;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPasswordField;


//add user page
public class AddUser implements WindowListener{

	private JFrame frameAddUser;
	private JTextField textFieldID;
	private JTextField textFieldFname;
	private JTextField textFieldLname;
	private JTextField textFieldEmail;
	private JPasswordField passwordverField;
	private JPasswordField passwordField;
	private JLabel lblDigits;
	private JLabel lblpasswordVer;
	private JLabel lblemailIsRequired;
	private JLabel lblmsg;
	private SqliteConnection db;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddUser window = new AddUser();
					window.frameAddUser.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public AddUser() {
		db = new SqliteConnection();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameAddUser = new JFrame();
		frameAddUser.setBounds(100, 100, 838, 558);
		frameAddUser.setLocationRelativeTo(null);
		frameAddUser.setResizable(false);
		
		JLabel lblAddUser = new JLabel("Add user");
		lblAddUser.setBounds(365, 11, 123, 27);
		lblAddUser.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
		lblAddUser.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel IDLabel = new JLabel("ID");
		IDLabel.setBounds(82, 128, 64, 14);
		IDLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		
		JLabel FnameLabel = new JLabel("First name");
		FnameLabel.setBounds(82, 183, 71, 14);
		FnameLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		
		JLabel LnameLabel = new JLabel("Last name");
		LnameLabel.setBounds(82, 262, 75, 14);
		LnameLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		
		JLabel EmailLabel = new JLabel("Email");
		EmailLabel.setBounds(82, 337, 106, 14);
		EmailLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		
		textFieldID = new JTextField();
		textFieldID.setBounds(171, 122, 96, 27);
		textFieldID.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		textFieldID.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldID.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		textFieldID.setColumns(10);
		
		textFieldFname = new JTextField();
		textFieldFname.setBounds(171, 177, 86, 27);
		textFieldFname.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));

		textFieldFname.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldFname.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		textFieldFname.setColumns(10);
		textFieldLname = new JTextField();
		textFieldLname.setBounds(167, 256, 86, 27);
		textFieldLname.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		textFieldLname.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldLname.setColumns(10);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(167, 331, 155, 27);
		textFieldEmail.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		textFieldEmail.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldEmail.setColumns(10);
		
		JLabel PasswordLabel = new JLabel("Password");
		PasswordLabel.setBounds(415, 122, 81, 27);
		PasswordLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		
		JLabel MWLabel = new JLabel("Manager/Worker");
		MWLabel.setBounds(473, 248, 147, 42);
		MWLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		
		JComboBox comboBoxMW = new JComboBox();
		comboBoxMW.setBounds(624, 247, 134, 45);
		comboBoxMW.setModel(new DefaultComboBoxModel(new String[] {"Manager", "Worker"}));
		comboBoxMW.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblemailIsRequired = new JLabel("[*email is required if user is manager]");
		lblemailIsRequired.setBounds(332, 340, 234, 14);
		lblemailIsRequired.setForeground(Color.GRAY);
		
		lblmsg = new JLabel("");
		lblmsg.setBounds(212, 461, 374, 24);
		lblmsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblmsg.setForeground(Color.RED);
		lblmsg.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
		
		JLabel lblPasswordVerfication = new JLabel("Password verfication");
		lblPasswordVerfication.setBounds(415, 167, 141, 27);
		lblPasswordVerfication.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		
		lblpasswordVer = new JLabel("[*password verefication must be the same as the password]");
		lblpasswordVer.setForeground(Color.GRAY);
		lblpasswordVer.setBackground(Color.WHITE);
		lblpasswordVer.setBounds(415, 205, 357, 27);
		frameAddUser.getContentPane().add(lblpasswordVer);
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.setBounds(392, 432, 96, 23);
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				greyWriting();
				lblmsg.setText("");
				if(textFieldID.getText().equals("")||textFieldLname.getText().equals("")||textFieldFname.getText().equals("")
							||passwordField.getText().equals("")||passwordverField.getText().equals(""))
					//checking if one of the fields is empty
				{
					lblmsg.setText("Please fill all the fields");
				}
				else
					if(textFieldID.getText().length()!=9)
						lblmsg.setText("ID must be 9 numbers length exactly ");
					else
						if(!(textFieldID.getText().matches("[0-9]+")))
							lblmsg.setText("ID must be numbers only");
					else
						if(textFieldFname.getText().length()>20)
							lblmsg.setText("First name must be between 1-20 characters");
						else
							if(!(textFieldFname.getText().matches("[a-zA-Z]+")))
								lblmsg.setText("First name must be letters only");
						else
							if(textFieldLname.getText().length()>20)
								lblmsg.setText("Last name must be between 1-20 characters");
							else
								if(!(textFieldLname.getText().matches("[a-zA-Z]+")))
									lblmsg.setText("Last name must be letters only");
									else
										if(!(passwordField.getText().equals(passwordverField.getText())))//checking password verification and passsword field
											lblpasswordVer.setForeground(Color.red);
										else
											if(passwordField.getText().length()>20||passwordverField.getText().length()>20)
												lblmsg.setText("Passwords must be between 1-20 characters");
								
				else//else if all fields are full and passwords are equal
					{
						if(((String)(comboBoxMW.getSelectedItem())).equals("Manager"))//check if we have manager
							{
								if(textFieldEmail.getText().equals(""))//if email field is empty
									lblemailIsRequired.setForeground(Color.red);
								else
									if(!(isValidEmailAddress(textFieldEmail.getText())))
									lblmsg.setText("Email must be valid");
								else//if every field is filled then add manager
									{
										User m = new Manager(textFieldID.getText(),textFieldFname.getText(),textFieldLname.getText(),textFieldEmail.getText(),passwordField.getText());
										boolean flag = db.insertUser(m);
										
										if(flag)
											{
											clearFields();
											greyWriting();
											}
									}
							}
						else//if user is worker
						{
								User w = new Worker(textFieldID.getText(),textFieldFname.getText(),textFieldLname.getText(),passwordField.getText());
								boolean flag = db.insertUser(w);
								
								if(flag)
									{
										clearFields();
										greyWriting();
									}
						}
						
					}
				
			}
		});
		
		lblDigits = new JLabel("[9 digits]");
		lblDigits.setBounds(271, 131, 64, 14);
		lblDigits.setForeground(Color.GRAY);
		
		
		
		
		
		passwordverField = new JPasswordField();
		passwordverField.setBounds(566, 167, 92, 27);
		passwordverField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordverField.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		passwordField.setBounds(565, 124, 94, 29);
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		frameAddUser.getContentPane().setLayout(null);
		frameAddUser.getContentPane().add(EmailLabel);
		frameAddUser.getContentPane().add(textFieldEmail);
		frameAddUser.getContentPane().add(lblAddUser);
		frameAddUser.getContentPane().add(lblemailIsRequired);
		frameAddUser.getContentPane().add(IDLabel);
		frameAddUser.getContentPane().add(textFieldID);
		frameAddUser.getContentPane().add(lblDigits);
		frameAddUser.getContentPane().add(LnameLabel);
		frameAddUser.getContentPane().add(textFieldLname);
		frameAddUser.getContentPane().add(FnameLabel);
		frameAddUser.getContentPane().add(textFieldFname);
		frameAddUser.getContentPane().add(lblPasswordVerfication);
		frameAddUser.getContentPane().add(passwordverField);
		frameAddUser.getContentPane().add(MWLabel);
		frameAddUser.getContentPane().add(PasswordLabel);
		frameAddUser.getContentPane().add(passwordField);
		frameAddUser.getContentPane().add(comboBoxMW);
		frameAddUser.getContentPane().add(btnAddUser);
		frameAddUser.getContentPane().add(lblmsg);
		
		
	}
	private void clearFields()
	{
		textFieldEmail.setText("");
		textFieldID.setText("");
		textFieldLname.setText("");
		textFieldFname.setText("");
		passwordverField.setText("");
		passwordField.setText("");
		lblmsg.setText("");
	}
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}
	public static boolean isAlpha(String name) {
	    return name.matches("[a-zA-Z]+");
	}
	private void greyWriting()//returns all the writings to grey 
	{
		lblDigits.setForeground(Color.GRAY);
		lblpasswordVer.setForeground(Color.GRAY);
		lblemailIsRequired.setForeground(Color.GRAY);

		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		((Window) arg0.getSource()).dispose();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
