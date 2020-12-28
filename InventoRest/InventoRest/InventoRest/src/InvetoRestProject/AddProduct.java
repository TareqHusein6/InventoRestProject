package InvetoRestProject;

import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import InvetoRestProject.classes.Product;
import javax.swing.JComboBox;
import java.awt.SystemColor;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//add product page
public class AddProduct implements WindowListener {

	private static JFrame frame;
	private static JTextField nameField;
	private static JTextField quantityField;
	private static JTextField priceField;
	private static JTextArea textAreaDisc;
	private static JDateChooser expDateChooser;
	private static JDateChooser buyDateChooser;
	private static String workerId;
	private static JComboBox<String> categoryComboBox;
	private static JComboBox<String> ProviderComboBox;
	private JLabel priceStar;
	private JLabel nameStar;
	private JLabel msg;
	private JLabel quantityStar;
	private JLabel expDateStar;
	private JLabel BuyDateStar;
	private JLabel CategoryNameStar;
	private JLabel ProviderNameStar;
	private JScrollPane scrollPane;
	private static String floatRegexp="^(\\d+\\.)?\\d+$";//checks double number
	private static SqliteConnection db;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		 String wId=args[0];//saves the id of the worker

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddProduct window = new AddProduct(wId);
					

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
	public AddProduct(String workerId) {
		db = new SqliteConnection();

		this.workerId = workerId;
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

		JLabel lblNewLabel = new JLabel("Add Product");
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(356, 36, 138, 20);
		frame.getContentPane().add(lblNewLabel);

		nameField = new JTextField();
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		nameField.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		nameField.setBounds(153, 124, 146, 26);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);

		quantityField = new JTextField();
		quantityField.setHorizontalAlignment(SwingConstants.CENTER);
		quantityField.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		quantityField.setColumns(10);
		quantityField.setBounds(153, 257, 146, 26);
		frame.getContentPane().add(quantityField);

		priceField = new JTextField();
		priceField.setHorizontalAlignment(SwingConstants.CENTER);
		priceField.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		priceField.setColumns(10);
		priceField.setBounds(153, 325, 146, 26);
		frame.getContentPane().add(priceField);

		JLabel label = new JLabel("Name");
		label.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		label.setBounds(31, 128, 91, 20);
		frame.getContentPane().add(label);

		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblDescription.setBounds(31, 195, 91, 20);
		frame.getContentPane().add(lblDescription);

		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblQuantity.setBounds(31, 261, 91, 20);
		frame.getContentPane().add(lblQuantity);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblPrice.setBounds(31, 329, 91, 20);
		frame.getContentPane().add(lblPrice);

		JLabel lblProviderName = new JLabel("Provider Name");
		lblProviderName.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblProviderName.setBounds(437, 341, 133, 20);
		frame.getContentPane().add(lblProviderName);

		JLabel lblCategoryname = new JLabel("Category Name");
		lblCategoryname.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblCategoryname.setBounds(437, 261, 133, 20);
		frame.getContentPane().add(lblCategoryname);

		JLabel lblBuyDate = new JLabel("Buy Date");
		lblBuyDate.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblBuyDate.setBounds(437, 129, 133, 20);
		frame.getContentPane().add(lblBuyDate);

		JLabel lblExpirationdate = new JLabel("Expiration Date");
		lblExpirationdate.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblExpirationdate.setBounds(437, 195, 133, 20);
		frame.getContentPane().add(lblExpirationdate);

		 expDateChooser = new JDateChooser();
		expDateChooser.setDateFormatString("yyyy-MM-dd");
		expDateChooser.setBounds(635, 195, 91, 20);
		frame.getContentPane().add(expDateChooser);

		 buyDateChooser = new JDateChooser();
		buyDateChooser.getCalendarButton().setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		buyDateChooser.setDateFormatString("yyyy-MM-dd");
		buyDateChooser.setBounds(635, 131, 91, 20);
		frame.getContentPane().add(buyDateChooser);
		
		ProviderComboBox = new JComboBox<String>();
		ProviderComboBox.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		ProviderComboBox.setBounds(608, 340, 118, 20);
		frame.getContentPane().add(ProviderComboBox);
		RefreshProviders();
		


		categoryComboBox = new JComboBox<String>();
		categoryComboBox.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		categoryComboBox.setBounds(608, 264, 118, 20);
		frame.getContentPane().add(categoryComboBox);
		RefreshCategories();

		msg = new JLabel("* mandatory boxes");
		msg.setForeground(SystemColor.textHighlight);
		msg.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		msg.setHorizontalAlignment(SwingConstants.CENTER);
		msg.setBounds(322, 451, 204, 32);
		frame.getContentPane().add(msg);

		nameStar = new JLabel("*");
		nameStar.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		nameStar.setBounds(306, 124, 46, 14);
		frame.getContentPane().add(nameStar);

		 quantityStar = new JLabel("*");
		quantityStar.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		quantityStar.setBounds(306, 267, 46, 14);
		frame.getContentPane().add(quantityStar);

		 expDateStar = new JLabel("*");
		expDateStar.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		expDateStar.setBounds(736, 197, 46, 14);
		frame.getContentPane().add(expDateStar);

		BuyDateStar = new JLabel("*");
		BuyDateStar.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		BuyDateStar.setBounds(736, 135, 46, 14);
		frame.getContentPane().add(BuyDateStar);

		CategoryNameStar = new JLabel("*");
		CategoryNameStar.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		CategoryNameStar.setBounds(736, 267, 46, 14);
		frame.getContentPane().add(CategoryNameStar);

		ProviderNameStar = new JLabel("*");
		ProviderNameStar.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		ProviderNameStar.setBounds(736, 343, 46, 14);
		frame.getContentPane().add(ProviderNameStar);

		textAreaDisc = new JTextArea();
		textAreaDisc.setColumns(20);
		textAreaDisc.setBounds(191, 416, 62, 22);
		frame.getContentPane().add(textAreaDisc);

		scrollPane = new JScrollPane(textAreaDisc);
		scrollPane.setBounds(153, 176, 146, 70);
		frame.getContentPane().add(scrollPane);

		JButton btnNewButton = new JButton("Add Category");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddCategory.main(null);
				
			}
		});
		btnNewButton.setBounds(437, 307, 113, 23);
		frame.getContentPane().add(btnNewButton);

		JLabel lblOrAddA = new JLabel("or add a new category");
		lblOrAddA.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrAddA.setBounds(437, 292, 133, 14);
		frame.getContentPane().add(lblOrAddA);

		JButton btnAddProvider = new JButton("Add Provider");
		btnAddProvider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddProvider.main(null);
			}
		});
		btnAddProvider.setBounds(437, 385, 113, 23);
		frame.getContentPane().add(btnAddProvider);

		JLabel lblOrAddA_1 = new JLabel("or add a new provider");
		lblOrAddA_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrAddA_1.setBounds(437, 368, 133, 14);
		frame.getContentPane().add(lblOrAddA_1);
		
		
		JButton btnAddProduct = new JButton("Add Product");
		/*
		 * add product listener
		 */
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				productAdd();
			}
		});
		JButton btnRefreshCategories = new JButton("Refresh Categories");
		btnRefreshCategories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RefreshCategories();
			}
		});
		btnRefreshCategories.setBounds(611, 300, 195, 29);
		frame.getContentPane().add(btnRefreshCategories);
		
		JButton btnRefreshProviders = new JButton("Refresh Providers");
		btnRefreshProviders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RefreshProviders();
			}
		});
		btnRefreshProviders.setBounds(611, 376, 195, 29);
		frame.getContentPane().add(btnRefreshProviders);
		
		btnAddProduct.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnAddProduct.setBounds(369, 494, 138, 23);
		frame.getContentPane().add(btnAddProduct);
		
		priceStar = new JLabel("*");
		priceStar.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		priceStar.setBounds(309, 335, 46, 14);
		frame.getContentPane().add(priceStar);

	}
	//function of adding product
	public static void productAdd()
	{
		String expdate = ((JTextField)expDateChooser.getDateEditor().getUiComponent()).getText();
		String buydate = ((JTextField)buyDateChooser.getDateEditor().getUiComponent()).getText();
		
		
		try {
				Date exp=null;
				Date buy=null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
				exp = sdf.parse(expdate);
				buy = sdf.parse(buydate);
				
				if(nameField.getText().equals("")||quantityField.getText().equals("")||
						priceField.getText().equals("")||
						expdate.equals("")||buydate.equals(""))
					{
						JOptionPane.showMessageDialog(null,"Please fill all mandatory fields.");
					}
				else
					if(!(quantityField.getText().matches("[0-9]+")))//check quantity field
					{
						JOptionPane.showMessageDialog(null,"Quantity must be only numbers");
					}
					else
						if(!(priceField.getText().matches(floatRegexp)))//check price field
						{
							JOptionPane.showMessageDialog(null,"Price must be only numbers with a dot(can be real)");
						}
						else
							if(exp.before(buy)||exp.equals(buy))
							{
								JOptionPane.showMessageDialog(null,"Expiration date must be after buy date");
							}
						else
							{					
								Product p =new Product(nameField.getText(),
										textAreaDisc.getText(),
										Integer.parseInt(quantityField.getText()),
										Double.parseDouble(priceField.getText()),
										expdate,
										buydate,
										categoryComboBox.getSelectedItem().toString(),
										ProviderComboBox.getSelectedItem().toString(),
										workerId);
								boolean flag = db.insertProduct(p);
								
								if(flag)
									{
										showMessage("Product Added successfuly","success");
									 	frame.dispose();
									}
								else
									showMessage("Product was not Added successfuly","Error");
							}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	//function for showing messages
		public static void showMessage(String infoMessage,String titleBar)
		{
			JOptionPane.showMessageDialog(null,infoMessage,titleBar,JOptionPane.INFORMATION_MESSAGE);
		}
	//refreshes categories combobox
	public void RefreshCategories()
	{
		//fills the combox with providers names
		String[] cnames = db.categoriesNames();
		
		categoryComboBox.setModel(new DefaultComboBoxModel<String>(cnames));
	}
	//refreshes providers combobox
	public void RefreshProviders()
	{
		//fills the combox with categories names
		String[] pnames = db.providersNames();
		
		ProviderComboBox.setModel(new DefaultComboBoxModel<String>(pnames));
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
