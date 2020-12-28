package InvetoRestProject;

import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import InvetoRestProject.classes.Provider;
import java.awt.Font;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//Add Provider page
public class AddProvider implements WindowListener{

	private JFrame frame;
	private JTextField textFieldName;
	private JTextField textFieldEmail;
	private JLabel lblmsg;
	private JLabel lblPhone;
	private JTextField textFieldPhone;
	private SqliteConnection db;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddProvider window = new AddProvider();
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
	public AddProvider() {
		db = new SqliteConnection();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 838, 558);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblName.setBounds(288, 174, 69, 20);
		frame.getContentPane().add(lblName);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblEmail.setBounds(288, 242, 69, 20);
		frame.getContentPane().add(lblEmail);
		
		textFieldName = new JTextField();
		textFieldName.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		textFieldName.setBounds(427, 171, 146, 26);
		frame.getContentPane().add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		textFieldEmail.setBounds(427, 239, 146, 26);
		frame.getContentPane().add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JLabel lblAddProvider = new JLabel("Add Provider");
		lblAddProvider.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
		lblAddProvider.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddProvider.setBounds(356, 36, 138, 20);
		frame.getContentPane().add(lblAddProvider);
		
		lblmsg = new JLabel("");
		lblmsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblmsg.setBounds(288, 452, 285, 20);
		frame.getContentPane().add(lblmsg);
		
		JButton btnAddProvider = new JButton("Add Provider");
		btnAddProvider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(textFieldName.getText().equals("")||textFieldEmail.getText().equals("")||textFieldPhone.getText().contentEquals(""))
				//checking if one of the fields is empty
				{
					lblmsg.setText("Please fill all the fields");
				}
					else
						if(!(isValidEmailAddress(textFieldEmail.getText())))
							lblmsg.setText("Email must be valid");
						else
							if(!(textFieldPhone.getText().matches("[0-9]+")))
								lblmsg.setText("Phone number must be numbers only.");
							else
								if(textFieldPhone.getText().length()>10)
									lblmsg.setText("Phone number must be less or equal to 10 digits.");
				
								else//if every field is filled then add manager
								{
									Provider p = new Provider(textFieldName.getText(),textFieldEmail.getText(),textFieldPhone.getText());
									boolean flag = db.insertProvider(p);
									
									if(flag)
										{
											frame.dispose();
											clearFields();
										}
								}
			}
		});
		btnAddProvider.setBounds(379, 350, 115, 29);
		frame.getContentPane().add(btnAddProvider);
		
		lblPhone = new JLabel("Phone");
		lblPhone.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblPhone.setBounds(288, 298, 69, 20);
		frame.getContentPane().add(lblPhone);
		
		textFieldPhone = new JTextField();
		textFieldPhone.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		textFieldPhone.setBounds(427, 295, 146, 26);
		frame.getContentPane().add(textFieldPhone);
		textFieldPhone.setColumns(10);
		
		
	}
	private void clearFields()
	{
		textFieldEmail.setText("");
		textFieldName.setText("");
		textFieldPhone.setText("");
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
