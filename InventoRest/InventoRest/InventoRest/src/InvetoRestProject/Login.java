package InvetoRestProject; 

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import InvetoRestProject.classes.MyButton;
import InvetoRestProject.classes.RoundedCornerBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

//login page window form

public class Login {

	private static JFrame frmInventorest;
	private static JTextField txtUserId;
	private static JPasswordField pwdPassword;
	private static JLabel lblUserIdIcon;
	private static JLabel lbLockIcon;
	private static SqliteConnection db;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmInventorest.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static JLabel message;
	
	/**
	 * Create the application.
	 */
	public Login() {
		db = new SqliteConnection();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInventorest = new JFrame();
		frmInventorest.setTitle("InventoRest");
		frmInventorest.setResizable(false);
		frmInventorest.setExtendedState(frmInventorest.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		
		frmInventorest.setUndecorated(true);//
		
		//setting the frame to maximum screen size
		frmInventorest.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frmInventorest.validate();
		
		
		JLabel lblInventorest = new JLabel("InventoRest");
		lblInventorest.setBounds(28, 0, 503, 128);
		lblInventorest.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 84));
		lblInventorest.setForeground(Color.decode("#35aadf"));
		
		JLabel StockIcon = new JLabel("");
		StockIcon.setBounds(526, 26, 97, 88);
		StockIcon.setIcon(new ImageIcon(getClass().getResource("/images/stock.png")));
		
		txtUserId = new JTextField(){
			@Override protected void paintComponent(Graphics g) {
				    if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
				      Graphics2D g2 = (Graphics2D) g.create();
				      g2.setPaint(getBackground());
				      g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
				          0, 0, getWidth() - 1, getHeight() - 1));
				      g2.dispose();
				    }
				    super.paintComponent(g);
				  }
				  @Override public void updateUI() {
				    super.updateUI();
				    setOpaque(false);
				    setBorder(new RoundedCornerBorder());
				  }
				};

		txtUserId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					login();
			}
		});
		txtUserId.setBounds(533, 220, 300, 67);
//		txtUserId.addKeyListener(new loginEnterListener());
		txtUserId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(txtUserId.getText().equals("UserID"))
					txtUserId.setText("");
				txtUserId.setForeground(Color.black);
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtUserId.getText().equals(""))
					{
					txtUserId.setText("UserID");
					txtUserId.setForeground(Color.LIGHT_GRAY);
					}
			}
		});
		txtUserId.setHorizontalAlignment(SwingConstants.CENTER);
		txtUserId.setFont(new Font("Tahoma", Font.PLAIN, 50));
		txtUserId.setForeground(Color.LIGHT_GRAY);
		txtUserId.setBackground(Color.WHITE);
		txtUserId.setText("UserID");
		txtUserId.setColumns(10);
		
		MyButton LoginButton = new MyButton("Login");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		LoginButton.setBounds(611, 466, 145, 48);


		LoginButton.setFont(new Font("Tahoma", Font.BOLD, 25));
		LoginButton.setBackground(Color.decode("#35aadf"));
		
		pwdPassword = new JPasswordField(){
			  @Override protected void paintComponent(Graphics g) {
				    if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
				      Graphics2D g2 = (Graphics2D) g.create();
				      g2.setPaint(getBackground());
				      g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
				          0, 0, getWidth() - 1, getHeight() - 1));
				      g2.dispose();
				    }
				    super.paintComponent(g);
				  }
				  @Override public void updateUI() {
				    super.updateUI();
				    setOpaque(false);
				    setBorder(new RoundedCornerBorder());
				  }
				};
		pwdPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					login();
			}
		});
		pwdPassword.setBounds(533, 353, 300, 67);
//		pwdPassword.addKeyListener(new loginEnterListener());
		pwdPassword.setForeground(Color.LIGHT_GRAY);
		pwdPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(pwdPassword.getText().equals("Password"))
					pwdPassword.setText("");
				pwdPassword.setForeground(Color.black);
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(pwdPassword.getText().equals(""))
					{
						pwdPassword.setText("Password");
						pwdPassword.setForeground(Color.LIGHT_GRAY);
					}
			}
		});
		pwdPassword.setHorizontalAlignment(SwingConstants.CENTER);
		pwdPassword.setText("Password");
		
		lblUserIdIcon = new JLabel("");
		lblUserIdIcon.setBounds(430, 220, 85, 63);
		lblUserIdIcon.setIcon(new ImageIcon(getClass().getResource("/images/user.png")));
		lblUserIdIcon.setHorizontalAlignment(SwingConstants.CENTER);
		
		lbLockIcon = new JLabel("");
		lbLockIcon.setBounds(430, 341, 97, 79);
		lbLockIcon.setIcon(new ImageIcon(getClass().getResource("/images/password1.png")));
		lbLockIcon.setHorizontalAlignment(SwingConstants.CENTER);
		
		message = new JLabel("");
		message.setBounds(397, 513, 561, 55);
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(new Font("Quicksand Medium", Font.BOLD, 24));
		
		MyButton btnExit = new MyButton("Exit");
		btnExit.setBounds(587, 566, 200, 62);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				frmInventorest.dispose();
			}
		});
		btnExit.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
		btnExit.setForeground(Color.RED);
		frmInventorest.getContentPane().setLayout(null);
		frmInventorest.getContentPane().add(lblInventorest);
		frmInventorest.getContentPane().add(StockIcon);
		frmInventorest.getContentPane().add(lbLockIcon);
		frmInventorest.getContentPane().add(pwdPassword);
		frmInventorest.getContentPane().add(lblUserIdIcon);
		frmInventorest.getContentPane().add(txtUserId);
		frmInventorest.getContentPane().add(btnExit);
		frmInventorest.getContentPane().add(message);
		frmInventorest.getContentPane().add(LoginButton);
		
	}
	public static void login()
	{
		try {
			if(!(txtUserId.getText().equals(""))&&!(pwdPassword.getText().equals(""))
					&&!(txtUserId.getText().equals("UserID"))&&!(pwdPassword.getText().equals("Password")))
			{
				
				int count=db.checkUser(txtUserId.getText(), pwdPassword.getText());
				if(count == 1)
				{
					Object obj=db.managerOrUser(txtUserId.getText());//returns the column as object
					
					String[] s=new String[2];
					s[0]=txtUserId.getText();
					
					if(obj instanceof String)//checking if the result is string
					{
						
						if(((String)obj).equals("w"))//checking if it's a worker
								s[1]="w";
						else
							if(((String)obj).equals("m"))
								s[1]="m";	
						Home.main(s);//enter the manager page
						frmInventorest.dispose();
					}
				}
				else {
					message.setText("Username and password are incorrect");
				}
			}
			else
				message.setText("Please fill all the fields");
				
		}catch(Exception e){
			e.printStackTrace();
			
		}			
	}

}
