package InvetoRestProject;

import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PrinterException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

// order details page with table of order details after clicking on an order 
public class ShowOrderDetails implements WindowListener{

	private JFrame frame;
	private static SqliteConnection db;
	private static JTable orderDetailsTable;
	private static int orderId;
	private String userType;
	private static JTextField textFieldSearch;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		String orderId=args[0];//saves the id of user
		String userType=args[1];//saves user type
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					ShowOrderDetails window = new ShowOrderDetails(orderId,userType);
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
	public ShowOrderDetails(String orderId,String userType) {
		db=new SqliteConnection();
		this.orderId=Integer.parseInt(orderId);
		this.userType=userType;
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
		
		JLabel lblNewLabel = new JLabel("OrderDetails");
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(344, 24, 157, 25);
		frame.getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPaneOrderDetails = new JScrollPane();
		scrollPaneOrderDetails.setBounds(167, 91, 433, 332);
		frame.getContentPane().add(scrollPaneOrderDetails);
		orderDetailsTable = new JTable();
		orderDetailsTable.setModel(DbUtils.resultSetToTableModel(db.selectOrderDetails(this.orderId)));
		db.closeConnection();
		scrollPaneOrderDetails.setViewportView(orderDetailsTable);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					orderDetailsTable.print();
				} catch (PrinterException e) {
					// TODO Auto-generated catch block
					showMessage("Problem with printer","Error");
				}
			}
		});
		btnPrint.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		btnPrint.setBounds(560, 445, 89, 37);
		frame.getContentPane().add(btnPrint);
		
		JButton btnSaveAsPdf = new JButton("Save As PDF");
		SaveAsPdfListener sapOrderDetails = new SaveAsPdfListener(orderDetailsTable,"orderDetails-orderNumber"+this.orderId);
		btnSaveAsPdf.addActionListener(sapOrderDetails);
		btnSaveAsPdf.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		btnSaveAsPdf.setBounds(675, 445, 142, 37);
		frame.getContentPane().add(btnSaveAsPdf);
		
		JButton btnDelete = new JButton("Delete Product from Order");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				DefaultTableModel model = (DefaultTableModel) orderDetailsTable.getModel();
				//get selected row index
				int selectedRowIndex = orderDetailsTable.getSelectedRow();
				//gets id value at the selected row 
				int productId=Integer.parseInt(orderDetailsTable.getModel().getValueAt(selectedRowIndex, 2).toString());
				
					if(db.removeOrderProduct(productId,orderId))
						model.removeRow(selectedRowIndex);//removes row from table	
					else
						showMessage("Couldn't remove Order", "Error");
				}catch(ArrayIndexOutOfBoundsException e)
				{
					showMessage("Please select a row first or refresh the table.","Select A Row");
				}
			}
		});
		btnDelete.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
		btnDelete.setBounds(36, 450, 247, 29);
		frame.getContentPane().add(btnDelete);
		
		JLabel lblSearch = new JLabel("Search by Product Name :");
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearch.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		lblSearch.setBounds(461, 60, 190, 18);
		frame.getContentPane().add(lblSearch);
		
		textFieldSearch = new JTextField();
		textFieldSearch.setBounds(661, 60, 111, 23);
		frame.getContentPane().add(textFieldSearch);
		textFieldSearch.setColumns(10);
		textFieldSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
						searchProducts();
			}
		});
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textFieldSearch.getText().equals(""))
					searchProducts();
			}
		});
		
		btnSearch.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnSearch.setBounds(641, 111, 89, 23);
		frame.getContentPane().add(btnSearch);
		
		JButton btnLoadOrderDetails = new JButton("Load Order Details");
		btnLoadOrderDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				orderDetailsTable.setModel(DbUtils.resultSetToTableModel(db.selectOrderDetails(orderId)));
				db.closeConnection();
			}
		});
		btnLoadOrderDetails.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnLoadOrderDetails.setBounds(20, 60, 172, 23);
		frame.getContentPane().add(btnLoadOrderDetails);
		
		if(userType.equals("w"))
		{
			frame.remove(btnDelete);
			frame.remove(btnSaveAsPdf);
			frame.remove(btnPrint);
		}
	}
	//function for showing messages
	public static void showMessage(String infoMessage,String titleBar)
	{
		JOptionPane.showMessageDialog(null,infoMessage,titleBar,JOptionPane.INFORMATION_MESSAGE);
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
	//function to search products by name
	private static void searchProducts()
		{
				//search the and display the table
				String keyword=textFieldSearch.getText();
				
				orderDetailsTable.setModel(DbUtils.resultSetToTableModel(db.searchOrderDetails(keyword,orderId)));
				db.closeConnection();
		}
}
