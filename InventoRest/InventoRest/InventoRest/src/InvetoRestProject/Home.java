package InvetoRestProject;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import InvetoRestProject.classes.LogoutButton;
import InvetoRestProject.classes.MyButton;
import InvetoRestProject.classes.OrderDetails;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.toedter.calendar.JDateChooser;

//home page for worker and manager

public class Home {

	private JFrame frmManager;
	private static JTable usersTable;
	private static JTable providersTable;
	private static JTable productsTable;
	private JTable providersOrdersTable;
	private static JTable ordersTable;
	private static String userId;//user id for future purposes
	private String userType;//manager or worker
	private static SqliteConnection db;
	private JTabbedPane tabbedPane;
	private static JTextField productsTextField;
	private static JTextField providersSearchField;
	private static JTextField UsersSearchField;
	private static JDateChooser searchDateChooserFrom;
	private static JDateChooser searchDateChooserTo;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		String userId=args[0];//saves the id of the user
		String userType=args[1];//saves type of user
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Home window = new Home(userId,userType);
					window.frmManager.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public Home(String userId,String userType) {
		
		db = new SqliteConnection();
		this.userId=userId;
		this.userType=userType;
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmManager = new JFrame();
		frmManager.setTitle("Manager");
		frmManager.getContentPane().setLayout(null);		
		frmManager.setResizable(false);
		frmManager.setExtendedState(frmManager.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		
		frmManager.setUndecorated(true);
		
		//setting the frame to maximum screen size
		frmManager.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frmManager.validate();
		
		JLabel lblUser = new JLabel("Manager");
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUser.setForeground(new Color(165, 42, 42));
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 52));
		lblUser.setBounds(485, 0, 256, 70);
		frmManager.getContentPane().add(lblUser);
		
		JLabel userIcon = new JLabel("");
		
		userIcon.setIcon(new ImageIcon(getClass().getResource("/images/manager.png")));
		userIcon.setBounds(755, 16, 100, 70);
		frmManager.getContentPane().add(userIcon);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		tabbedPane.setBounds(0, 69, 1178, 496);
		frmManager.getContentPane().add(tabbedPane);
		
		JPanel home = new JPanel();
		home.setLayout(null);
		home.setBackground(new Color(220, 220, 220));
		tabbedPane.addTab("Home", null, home, null);
		
		JButton btnProducts = new MyButton("Products");
		btnProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(1);
			}
		});
		btnProducts.setBounds(150, 50, 227, 99);
		home.add(btnProducts);
		

		
		JButton btnUsers = new MyButton("Users");
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(4);
			}
		});
		btnUsers.setBounds(150, 313, 227, 99);
		home.add(btnUsers);
		
		JButton btnOrders = new MyButton("Orders");
		btnOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(3);
			}
		});
		btnOrders.setBounds(786, 50, 227, 99);
		home.add(btnOrders);
		
		JButton btnProviders = new MyButton("Providers");
		btnProviders.setBounds(786, 313, 227, 99);
		home.add(btnProviders);
		btnProviders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(2);
			}
		});
		JPanel Mproducts = new JPanel();
		tabbedPane.addTab("Products", null, Mproducts, null);
		Mproducts.setBackground(new Color(220, 220, 220));
		Mproducts.setLayout(null);
		
		JScrollPane scrollPaneProducts = new JScrollPane();
		scrollPaneProducts.setBounds(105, 68, 967, 309);
		Mproducts.add(scrollPaneProducts);
		
		productsTable = new JTable(){
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
		
		scrollPaneProducts.setViewportView(productsTable);
		
		JButton btnLoadProductsData = new JButton("Load Products Data");
		btnLoadProductsData.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnLoadProductsData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					//changes result set to table model using function from jar file "rs2xml.jar"				
					productsTable.setModel(DbUtils.resultSetToTableModel(db.selectAll("Products")));
					db.closeConnection();
					
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnLoadProductsData.setBounds(36, 16, 169, 41);
		Mproducts.add(btnLoadProductsData);
		
		JButton btnAddProduct = new JButton("Add Product");
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] s= new String[1];
				s[0]=userId;
				AddProduct.main(s);
			}
		});
		btnAddProduct.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnAddProduct.setBounds(0, 393, 121, 35);
		Mproducts.add(btnAddProduct);
		
		JButton btnDeleteProduct = new JButton("Delete Product");
		btnDeleteProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeProduct();				
			}
		});
		btnDeleteProduct.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnDeleteProduct.setBounds(149, 393, 142, 35);
		Mproducts.add(btnDeleteProduct);
		
		JButton btnEditProduct = new JButton("Edit Product");
		btnEditProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//string to save all columns values to send after to edit product form
				try{
					String[] arg = new String[productsTable.getColumnCount()+1];
					//get selected row index
					int selectedRowIndex = productsTable.getSelectedRow();
					//gets values of all columns
					int i;
					for( i=0;i<arg.length-1;i++)
						arg[i]=productsTable.getModel().getValueAt(selectedRowIndex, i).toString();
					arg[i]=userId;
					EditProduct.main(arg);//enter the edit page
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					showMessage("Please select a row first or refresh the table.","Select A Row");
				}
				
			}
		});
		btnEditProduct.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnEditProduct.setBounds(301, 393, 133, 35);
		Mproducts.add(btnEditProduct);
		
		JButton printProductsbtn = new JButton("Print");
		printProductsbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					productsTable.print();
				} catch (PrinterException e) {
					// TODO Auto-generated catch block
					showMessage("Problem with printer","Error");
				}
			}
		});
		printProductsbtn.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		printProductsbtn.setBounds(798, 392, 89, 35);
		Mproducts.add(printProductsbtn);
		
		JButton saveAsPdfProductsbtn = new JButton("Save as pdf");
		SaveAsPdfListener sapProducts=new SaveAsPdfListener(productsTable,"ProductsTable");
		saveAsPdfProductsbtn.addActionListener(sapProducts);
		
		saveAsPdfProductsbtn.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		saveAsPdfProductsbtn.setBounds(915, 393, 133, 35);
		Mproducts.add(saveAsPdfProductsbtn);
		
		JLabel lblProducts = new JLabel("Products");
		lblProducts.setHorizontalAlignment(SwingConstants.CENTER);
		lblProducts.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblProducts.setBounds(538, 0, 151, 35);
		Mproducts.add(lblProducts);
		
		JLabel lblSearchProductBy = new JLabel("Search product by name:");
		lblSearchProductBy.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchProductBy.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		lblSearchProductBy.setBounds(699, 32, 188, 26);
		Mproducts.add(lblSearchProductBy);
		
		productsTextField = new JTextField();
		productsTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					searchProducts();	
			}
		});
		productsTextField.setBounds(879, 37, 142, 20);
		Mproducts.add(productsTextField);
		productsTextField.setColumns(10);
		
		JButton btnSearchProducts = new JButton("Search");
		btnSearchProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searchProducts();
			}
		});
		btnSearchProducts.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnSearchProducts.setBounds(1035, 34, 89, 23);
		Mproducts.add(btnSearchProducts);
		
		JButton btnShowCategories = new JButton("Show and Add Categories");
		btnShowCategories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddCategory.main(null);
			}
		});
		btnShowCategories.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnShowCategories.setBounds(472, 401, 217, 41);
		Mproducts.add(btnShowCategories);
		
		JPanel Mproviders = new JPanel();
		tabbedPane.addTab("Providers", null, Mproviders, null);
		Mproviders.setBackground(new Color(220, 220, 220));
		Mproviders.setLayout(null);
		
		JLabel lblProviders = new JLabel("Providers");
		lblProviders.setBounds(538, 16, 151, 35);
		lblProviders.setHorizontalAlignment(SwingConstants.CENTER);
		lblProviders.setFont(new Font("Tahoma", Font.BOLD, 18));
		Mproviders.add(lblProviders);
		
		JScrollPane scrollPaneProviders = new JScrollPane();
		scrollPaneProviders.setBounds(386, 84, 464, 309);
		Mproviders.add(scrollPaneProviders);
		
		providersTable = new JTable(){
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
		scrollPaneProviders.setViewportView(providersTable);
		
		JButton btnLoadProvidersData = new JButton("Load Providers Data");
		btnLoadProvidersData.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnLoadProvidersData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//changes rs to table model using function from jar file "rs2xml.jar"
					providersTable.setModel(DbUtils.resultSetToTableModel(db.selectAll("Providers")));
					db.closeConnection();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnLoadProvidersData.setBounds(36, 32, 186, 49);
		Mproviders.add(btnLoadProvidersData);
		
		JButton btnAddProvider = new JButton("Add Provider");
		btnAddProvider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddProvider.main(null);
			}
		});
		btnAddProvider.setBounds(0, 409, 121, 35);
		btnAddProvider.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		Mproviders.add(btnAddProvider);
		
		JButton btnDeleteProvider = new JButton("Delete Provider");
		btnDeleteProvider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeProvider();
			}
		});
		btnDeleteProvider.setBounds(149, 409, 142, 35);
		btnDeleteProvider.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		Mproviders.add(btnDeleteProvider);
		
		JButton btnEditProvider = new JButton("Edit Provider");
		btnEditProvider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					//string to save all columns values to send after to edit Provider form
					String[] arg = new String[providersTable.getColumnCount()];
					//get selected row index
					int selectedRowIndex = providersTable.getSelectedRow();
					//gets values of all columns
					for(int i=0;i<arg.length;i++)
						arg[i]=(String)providersTable.getModel().getValueAt(selectedRowIndex, i);
					EditProvider.main(arg);//enter the worker page
				}catch(ArrayIndexOutOfBoundsException e)
				{
					showMessage("Please select a row first or refresh the table.","Select A Row");
				}
			}
		});
		btnEditProvider.setBounds(333, 409, 121, 35);
		btnEditProvider.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		Mproviders.add(btnEditProvider);
		
		JButton PrintProviderbtn = new JButton("Print");
		PrintProviderbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					providersTable.print();
				} catch (PrinterException e) {
					// TODO Auto-generated catch block
					showMessage("Problem with printer","Error");
				}
			}
		});
		PrintProviderbtn.setBounds(756, 409, 133, 35);
		PrintProviderbtn.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		Mproviders.add(PrintProviderbtn);
		
		JButton saveAsPdfProviderbtn = new JButton("Save as pdf");
		SaveAsPdfListener sapProviders = new SaveAsPdfListener(providersTable,"ProvidersTable");
		saveAsPdfProviderbtn.addActionListener(sapProviders);
		
		saveAsPdfProviderbtn.setBounds(915, 409, 133, 35);
		saveAsPdfProviderbtn.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		Mproviders.add(saveAsPdfProviderbtn);
		
		JLabel SearchLabel = new JLabel("Search providers by name:");
		SearchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		SearchLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		SearchLabel.setBounds(722, 47, 188, 26);
		Mproviders.add(SearchLabel);
		
		providersSearchField = new JTextField();
		providersSearchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					searchProviders();
			}
		});
		providersSearchField.setColumns(10);
		providersSearchField.setBounds(902, 52, 142, 20);
		Mproviders.add(providersSearchField);
		
		JButton providersSearchbtn = new JButton("Search");
		providersSearchbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchProviders();
			}
		});
		providersSearchbtn.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		providersSearchbtn.setBounds(1058, 49, 89, 23);
		Mproviders.add(providersSearchbtn);
		
		JPanel Morders = new JPanel();
		tabbedPane.addTab("Orders", null, Morders, null);
		Morders.setBackground(new Color(220, 220, 220));
		Morders.setLayout(null);
		
		JLabel lblOrders = new JLabel("Orders");
		lblOrders.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrders.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblOrders.setBounds(538, 11, 151, 35);
		Morders.add(lblOrders);
		
		ordersTable = new JTable(){
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
		JButton btnLoadOrdersdata = new JButton("Load Orders Data");
		btnLoadOrdersdata.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnLoadOrdersdata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//changes rs to table model using function from jar file "rs2xml.jar"
					ordersTable.setModel(DbUtils.resultSetToTableModel(db.selectAll("Orders")));
					db.closeConnection();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnLoadOrdersdata.setBounds(358, 23, 170, 41);
		Morders.add(btnLoadOrdersdata);
		
		
		JScrollPane scrollPaneOrders = new JScrollPane();
		scrollPaneOrders.setBounds(386, 79, 464, 309);
		Morders.add(scrollPaneOrders);
		scrollPaneOrders.setViewportView(ordersTable);
		
		JButton btnLoadProvidersNames = new JButton("Load Providers Names");
		btnLoadProvidersNames.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnLoadProvidersNames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//changes rs to table model using function from jar file "rs2xml.jar"
					providersOrdersTable.setModel(DbUtils.resultSetToTableModel(db.selectProvidersNames()));
					db.closeConnection();
				}catch (Exception m) {
					m.printStackTrace();
				}
			}
		});
		btnLoadProvidersNames.setBounds(25, 20, 192, 47);
		Morders.add(btnLoadProvidersNames);
		
		providersOrdersTable = new JTable(){
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };//the providers names in orders tab
		
		JScrollPane scrollPaneProvOrders = new JScrollPane();
		scrollPaneProvOrders.setBounds(36, 128, 263, 252);
		Morders.add(scrollPaneProvOrders);
		scrollPaneProvOrders.setViewportView(providersOrdersTable);
		
		JButton btnCreateOrder = new JButton("Create Order");
		btnCreateOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					int selectedRowIndex = providersOrdersTable.getSelectedRow();
					String providerName= providersOrdersTable.getModel().getValueAt(selectedRowIndex, 0).toString();

					String[] s= new String[3];
					s[0]=providerName;//provider's Name
					s[1]=userId;//user's id
					s[2]=userType;//worker or manager
					//send provider name to create order form
					CreateOrder.main(s);
					
				}catch(ArrayIndexOutOfBoundsException m)
				{
					showMessage("Please select a row first or refresh the table.","Select A Row");
				}
			}
		});
		btnCreateOrder.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnCreateOrder.setBounds(96, 386, 121, 35);
		Morders.add(btnCreateOrder);
		
		JButton btnDeleteOrder = new JButton("Delete Order");
		btnDeleteOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				DefaultTableModel model = (DefaultTableModel) ordersTable.getModel();
				//get selected row index
				int selectedRowIndex = ordersTable.getSelectedRow();
				//gets id value at the selected row 
				int id=Integer.parseInt(ordersTable.getModel().getValueAt(selectedRowIndex, 0).toString());

					if(db.removeOrderDetails(id)&&db.removeOrder(id))
						model.removeRow(selectedRowIndex);//removes row from table	
					else
						showMessage("Couldn't remove Order", "Error");
				}catch(ArrayIndexOutOfBoundsException e)
				{
					showMessage("Please select a row first or refresh the table.","Select A Row");
				}
			}
		});
		btnDeleteOrder.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnDeleteOrder.setBounds(358, 405, 121, 35);
		Morders.add(btnDeleteOrder);
		
		JButton PrintOrderbtn = new JButton("Print");
		PrintOrderbtn.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		PrintOrderbtn.setBounds(756, 404, 133, 35);
		PrintOrderbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ordersTable.print();
				} catch (PrinterException e) {
					// TODO Auto-generated catch block
					showMessage("Problem with printer","Error");
				}
			}
		});
		Morders.add(PrintOrderbtn);
		
		
		JButton btnShowOrderDetails = new JButton("Show Order Details");
		btnShowOrderDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					//get selected row index
					int selectedRowIndex = ordersTable.getSelectedRow();
					//gets id value at the selected row 
					String id=ordersTable.getModel().getValueAt(selectedRowIndex, 0).toString();
					
					String[] s= new String[2];
					s[0]=id;
					s[1]=userType;
					ShowOrderDetails.main(s);
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					showMessage("Please select a row first or refresh the table.","Select A Row");
				}
			}
		});
		btnShowOrderDetails.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnShowOrderDetails.setBounds(924, 234, 161, 35);
		Morders.add(btnShowOrderDetails);
		
		JButton btnSaveAsPdfOrder = new JButton("Save As Pdf");
		SaveAsPdfListener sapOrders=new SaveAsPdfListener(ordersTable,"OrdersTable");
		btnSaveAsPdfOrder.addActionListener(sapOrders);
		
		btnSaveAsPdfOrder.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		btnSaveAsPdfOrder.setBounds(937, 404, 133, 35);
		Morders.add(btnSaveAsPdfOrder);
		
		JLabel lblChooseAProvider = new JLabel("Choose a provider and then press create order button");
		lblChooseAProvider.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		lblChooseAProvider.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseAProvider.setBounds(10, 93, 340, 24);
		Morders.add(lblChooseAProvider);
		
		JLabel lblChooseOrderThen = new JLabel("<html>Choose order then press show order details to look into order.</html>");
		lblChooseOrderThen.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		lblChooseOrderThen.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseOrderThen.setBounds(860, 178, 313, 51);
		Morders.add(lblChooseOrderThen);
		
		
		JLabel lblProvidersNames = new JLabel("Providers Names");
		lblProvidersNames.setHorizontalAlignment(SwingConstants.CENTER);
		lblProvidersNames.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
		lblProvidersNames.setBounds(76, 72, 151, 24);
		Morders.add(lblProvidersNames);
		
		JButton btnUpdateProducts = new JButton("Update products");
		btnUpdateProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					//get selected row index
					int selectedRowIndex = ordersTable.getSelectedRow();
					//gets id value at the selected row 
					int id=Integer.parseInt(ordersTable.getModel().getValueAt(selectedRowIndex, 0).toString());
					
					//get the arraylist of all products in order id
					 List<OrderDetails> ods=new ArrayList<OrderDetails>();
					 ods=db.orderDetailsArray(id);
					
					 if(ods==null)
						 showMessage("no products in order","no products");
					 else
					 {
						 Boolean added=false;
						//change the orderDetails list to array
				         OrderDetails[] orderDetailsArray = new OrderDetails[ods.size()];
				         orderDetailsArray = ods.toArray(orderDetailsArray);
				         
				         //update orderDetails and products columns
						 for(int i=0;i<ods.size();i++)
							 //if products in order not added to stock yet
							 if(orderDetailsArray[i].getAdded()==0)
								 {
								 	db.updateProductsQuantity(orderDetailsArray[i]);
								 	db.updateAdded(orderDetailsArray[i]);
								 }
							 else//if products in order added already
							 {
								 showMessage("This order has already been added.","Added Already");
								 added=true;
								 break;
							 }
						 if(!added)//if the products were already in before start then skip this message
							 showMessage("Stock updated successflly","Success!");
						 db.closeConnection();
					 }

				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					showMessage("Please select a row first or refresh the table.","Select A Row");
				}
			}
		});
		btnUpdateProducts.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnUpdateProducts.setBounds(919, 351, 151, 29);
		Morders.add(btnUpdateProducts);
		
		JLabel lblNewLabel = new JLabel("<html>Choose the order that arrived to update the products in stock then click update products button.</html>");
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		lblNewLabel.setBounds(865, 280, 293, 67);
		Morders.add(lblNewLabel);
		
		JLabel SearchByDatelb = new JLabel("Search by date:");
		SearchByDatelb.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		SearchByDatelb.setBounds(906, 29, 133, 20);
		Morders.add(SearchByDatelb);
		
		searchDateChooserFrom = new JDateChooser();
		searchDateChooserFrom.getCalendarButton().setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		searchDateChooserFrom.setDateFormatString("yyyy-MM-dd");
		searchDateChooserFrom.setBounds(906, 76, 91, 20);
		Morders.add(searchDateChooserFrom);
		
		JButton btnSearchOrders = new JButton("Search");
		btnSearchOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchOrders();
			}
		});
		btnSearchOrders.setBounds(964, 125, 89, 23);
		Morders.add(btnSearchOrders);
		
		JLabel toLabel = new JLabel("To");
		toLabel.setHorizontalAlignment(SwingConstants.CENTER);
		toLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 11));
		toLabel.setBounds(995, 79, 32, 14);
		Morders.add(toLabel);
		
		searchDateChooserTo = new JDateChooser();
		searchDateChooserTo.getCalendarButton().setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		searchDateChooserTo.setDateFormatString("yyyy-MM-dd");
		searchDateChooserTo.setBounds(1022, 76, 91, 20);
		Morders.add(searchDateChooserTo);
		
		JLabel lblFrom = new JLabel("From");
		lblFrom.setHorizontalAlignment(SwingConstants.CENTER);
		lblFrom.setFont(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblFrom.setBounds(860, 80, 36, 14);
		Morders.add(lblFrom);
		
		JPanel Musers = new JPanel();
		
		tabbedPane.addTab("Users", null, Musers, null);
		Musers.setBackground(new Color(220, 220, 220));
		Musers.setLayout(null);
		
		//scrollPane for users table
		JScrollPane scrollPaneUsers = new JScrollPane();
		scrollPaneUsers.setBounds(120, 72, 967, 309);
		Musers.add(scrollPaneUsers);
		
		usersTable = new JTable(){
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
		scrollPaneUsers.setViewportView(usersTable);
		
		JLabel lblUsers = new JLabel("Users");
		lblUsers.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsers.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblUsers.setBounds(542, 11, 151, 35);
		Musers.add(lblUsers);
		
		JButton btnLoadUsersData = new JButton("Load Users Data");
		btnLoadUsersData.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnLoadUsersData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//changes rs to table model using function from jar file "rs2xml.jar"
					usersTable.setModel(DbUtils.resultSetToTableModel(db.selectAll("Users")));
					db.closeConnection();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnLoadUsersData.setBounds(51, 20, 158, 41);
		Musers.add(btnLoadUsersData);
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddUser.main(null);
				
			}
		});
		
		btnAddUser.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnAddUser.setBounds(15, 397, 121, 35);
		
		Musers.add(btnAddUser);
		
		JButton btnDeleteUser = new JButton("Delete User");
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeUser();
			}
		});
		
		btnDeleteUser.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnDeleteUser.setBounds(164, 397, 121, 35);
		Musers.add(btnDeleteUser);
		
		JButton btnEditUser = new JButton("Edit User");
		btnEditUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					//string to save all columns values to send after to edit user form
					String[] arg = new String[usersTable.getColumnCount()];
					//get selected row index
					int selectedRowIndex = usersTable.getSelectedRow();
					//gets values of all columns
					for(int i=0;i<arg.length;i++)
						arg[i]=(String)usersTable.getModel().getValueAt(selectedRowIndex, i);
					EditUserFrm.main(arg);//enter the worker page
				}catch(ArrayIndexOutOfBoundsException e)
				{
					showMessage("Please select a row first or refresh the table.","Select A Row");
				}
			}
		});
		
		btnEditUser.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnEditUser.setBounds(316, 397, 121, 35);
		Musers.add(btnEditUser);
		
		JButton btnSaveAsPdf = new JButton("Save as pdf");
		//my listener of converting table to pdf when button is pressed
		SaveAsPdfListener sapUsers = new SaveAsPdfListener(usersTable,"UsersTable");
		btnSaveAsPdf.addActionListener(sapUsers);

		btnSaveAsPdf.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		btnSaveAsPdf.setBounds(930, 397, 133, 35);
		Musers.add(btnSaveAsPdf);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					usersTable.print();
				} catch (PrinterException e) {
					// TODO Auto-generated catch block
					showMessage("Problem with printer","Error");
				}
			}
		});
		btnPrint.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		
		btnPrint.setBounds(771, 397, 133, 35);
		Musers.add(btnPrint);
		
		JLabel searchUsersLabel = new JLabel("Search users by first name:");
		searchUsersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		searchUsersLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		searchUsersLabel.setBounds(724, 37, 188, 26);
		Musers.add(searchUsersLabel);
		
		UsersSearchField = new JTextField();
		UsersSearchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					searchUsers();
			}
		});
		UsersSearchField.setColumns(10);
		UsersSearchField.setBounds(918, 42, 142, 20);
		Musers.add(UsersSearchField);
		
		JButton SearchUsersbtn = new JButton("Search");
		SearchUsersbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchUsers();
			}
		});
		SearchUsersbtn.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		SearchUsersbtn.setBounds(1074, 39, 89, 23);
		Musers.add(SearchUsersbtn);
		
		JButton logout = new LogoutButton("Logout",frmManager);

		logout.setBounds(481, 572, 179, 43);
		frmManager.getContentPane().add(logout);
		
		JButton exitbtn = new MyButton("Exit");
		exitbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		exitbtn.setForeground(Color.RED);
		exitbtn.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
		exitbtn.setBounds(475, 631, 200, 62);
		frmManager.getContentPane().add(exitbtn);
		
		//returns full name as an array
		String[] fullNameArray = new String[2];
		fullNameArray = db.userName(userId);
		String fullName = "Welcome "+fullNameArray[0]+" "+fullNameArray[1];
		JLabel welcomeLabel = new JLabel(fullName);
		welcomeLabel.setFont(new Font("Yu Gothic UI", Font.BOLD | Font.ITALIC, 17));
		welcomeLabel.setBounds(34, 16, 346, 37);
		frmManager.getContentPane().add(welcomeLabel);
		
		if(this.userType.equals("w"))
		{
			lblUser.setText("Worker");
			userIcon.setIcon(new ImageIcon(getClass().getResource("/images/kitchen_worker.png")));
			
			btnProviders.setVisible(false);
			btnUsers.setVisible(false);

			tabbedPane.removeTabAt(4);
			tabbedPane.removeTabAt(2);
			ActionListener[] a = btnOrders.getActionListeners();
			btnOrders.removeActionListener(a[0]);
			btnOrders.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tabbedPane.setSelectedIndex(2);
				}
			});
			Mproducts.remove(btnDeleteProduct);
			Mproducts.remove(saveAsPdfProductsbtn);
			Mproducts.remove(printProductsbtn);
			
			Morders.remove(btnDeleteOrder);
			Morders.remove(PrintOrderbtn);
			Morders.remove(btnSaveAsPdfOrder);

		}
	}
	//function for showing messages
	private static void showMessage(String infoMessage,String titleBar)
	{
		JOptionPane.showMessageDialog(null,infoMessage,titleBar,JOptionPane.INFORMATION_MESSAGE);
	}
	//function to search products by name
	private static void searchProducts()
	{
			//search the and display the table
			String keyword=productsTextField.getText();
			
			productsTable.setModel(DbUtils.resultSetToTableModel(db.searchProducts(keyword)));
			db.closeConnection();
	}
	//function to search providers by name
	private static void searchProviders()
	{
		//search the and display the table
		String keyword=providersSearchField.getText();
		
		providersTable.setModel(DbUtils.resultSetToTableModel(db.searchProviders(keyword)));
		db.closeConnection();
	}
	//function to search users by First name
	private static void searchUsers()
	{
		//search the and display the table
		String keyword=UsersSearchField.getText();
				
		usersTable.setModel(DbUtils.resultSetToTableModel(db.searchUsers(keyword)));
		db.closeConnection();
	}
	//function to search orders by date
	private static void searchOrders()
	{
		
		String searchDateFrom = ((JTextField)searchDateChooserFrom.getDateEditor().getUiComponent()).getText();
		String searchDateTo = ((JTextField)searchDateChooserTo.getDateEditor().getUiComponent()).getText();
		if(searchDateFrom.equals("")||searchDateTo.equals(""))
		{
			showMessage("Choose dates first","Empty Fields");
		}
		else {
			Date dateFrom=null;
			Date dateTo=null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			try {
				dateFrom = sdf.parse(searchDateFrom);
				dateTo = sdf.parse(searchDateTo);
				
				if(dateTo.before(dateFrom))
				{
					showMessage("To-date must be after or equal from-date","Wrong dates");
				}
				else
				{
					ordersTable.setModel(DbUtils.resultSetToTableModel(db.searchOrders(searchDateFrom,searchDateTo)));
					db.closeConnection();	
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}
	//delete product button
	private static void removeProduct()
	{
		try {
			
			DefaultTableModel model = (DefaultTableModel) productsTable.getModel();
			//get selected row index
			int selectedRowIndex = productsTable.getSelectedRow();
			//gets id value at the selected row 
			int id=(int) productsTable.getModel().getValueAt(selectedRowIndex, 0);
			/*search orderdetails table to check if there is products
			 * of the same id before deleting
			 */
			int productsAmount = db.numberOfProductsInOrderDetails(id);
			if(productsAmount>0)
				showMessage("Can't remove products, it appears in order details table","Remove failed");
			else
				if(productsAmount==0)//if product doesn't appear in orderdetails table
				{			
					if(db.removeProduct(id))
						model.removeRow(selectedRowIndex);//removes row from table	
					else
						showMessage("Couldn't remove product,failure in database", "Error");
				}
				else//if there were sql Exception
					showMessage("Couldn't remove product,failure in database","Error");
		}catch(ArrayIndexOutOfBoundsException e)
			{
				showMessage("Please select a row first or refresh the table.","Select A Row");
			}
	}
	//delete provider button
	private static void removeProvider()
	{
		try {
			
			DefaultTableModel model = (DefaultTableModel) providersTable.getModel();
			//get selected row index
			int selectedRowIndex = providersTable.getSelectedRow();
			//gets id value at the selected row 
			String name=(String)providersTable.getModel().getValueAt(selectedRowIndex, 0);
			//check provider if exits in table
			int providersAmountInProdTable = db.numberOfProvidersInAtable(name,"Products");
			int providersAmountInOrderTable =db.numberOfProvidersInAtable(name, "Orders");
			
			if(providersAmountInProdTable>0&&providersAmountInOrderTable>0)
				showMessage("Can't remove provider, it appears in Orders and Products tables","Remove failed");
			else
				if(providersAmountInOrderTable>0)
					showMessage("Can't remove provider, it appears in Orders table","Remove failed");
				else
					if(providersAmountInProdTable>0)
						showMessage("Can't remove provider, it appears in Products table","Remove failed");	
			else
				if(providersAmountInProdTable==0&&providersAmountInOrderTable==0)
					//if product doesn't appear in orderdetails table
				{			
					if(db.removeProvider(name))
						model.removeRow(selectedRowIndex);//removes row from table	
					else
						showMessage("Couldn't remove provider,failure in database", "Error");
				}
				else//if there were sql Exception
					showMessage("Couldn't remove provider,failure in database","Error");
			
		}catch(ArrayIndexOutOfBoundsException e)
			{
				showMessage("Please select a row first or refresh the table.","Select A Row");
			}
	}
	//remove user button
	private static void removeUser()
	{
		try {
			
			DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
			//get selected row index
			int selectedRowIndex = usersTable.getSelectedRow();
			//gets id value at the selected row 
			String id=(String)usersTable.getModel().getValueAt(selectedRowIndex, 0);
			/*search orderdetails table to check if there is products
			 * of the same id before deleting
			 */
			int usersAmountInProdTable = db.numberOfUsersInAtable(id,"Products");
			int userAmountInOrderTable =db.numberOfUsersInAtable(id, "Orders");
			
			if(usersAmountInProdTable>0&&userAmountInOrderTable>0)
				showMessage("Can't remove user, it appears in Orders and Products tables","Remove failed");
			else
				if(userAmountInOrderTable>0)
					showMessage("Can't remove user, it appears in Orders table","Remove failed");
				else
					if(usersAmountInProdTable>0)
						showMessage("Can't remove user, it appears in Products table","Remove failed");	
			else
				if(usersAmountInProdTable==0&&userAmountInOrderTable==0)
					//if user doesn't appear in orderdetails table
				{			
					if(db.removeProvider(id))
						model.removeRow(selectedRowIndex);//removes row from table	
					else
						showMessage("Couldn't remove user,failure in database", "Error");
				}
				else//if there were sql Exception
					showMessage("Couldn't remove user,failure in database","Error");
			
		}catch(ArrayIndexOutOfBoundsException e)
			{
				showMessage("Please select a row first or refresh the table.","Select A Row");
			}
	}
}
