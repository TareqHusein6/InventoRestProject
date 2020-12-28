package InvetoRestProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import java.awt.Font;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
//add category page
public class AddCategory implements WindowListener{

	private JFrame frame;
	private JTextField CategoryField;
	private static SqliteConnection db;
	private static JTable categoriesTable;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddCategory window = new AddCategory();
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
	public AddCategory() {
		db = new SqliteConnection();
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 576, 459);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Add Category");
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(213, 11, 148, 27);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Category Name");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		lblNewLabel_1.setBounds(126, 77, 142, 27);
		frame.getContentPane().add(lblNewLabel_1);
		
		CategoryField = new JTextField();
		CategoryField.setHorizontalAlignment(SwingConstants.CENTER);
		CategoryField.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		CategoryField.setBounds(278, 74, 124, 30);
		frame.getContentPane().add(CategoryField);
		CategoryField.setColumns(10);
		
	

		JButton btnAddCategory = new JButton("Add Category");
		btnAddCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(CategoryField.getText().equals(""))
					showMessage("Fill Category name.","Error");
				else
					if(!(isAlpha(CategoryField.getText())))
						showMessage("Fill Category name with characters only.","Error");
					else
						{
							if(db.insertCategory(CategoryField.getText()))
								{
									categoriesTable.setModel(DbUtils.resultSetToTableModel(db.selectAll("Categories")));
									db.closeConnection();
								}
							else
								showMessage("Failed to insert Category","Error");
						}
			}
		});
		btnAddCategory.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
		btnAddCategory.setBounds(213, 115, 156, 30);
		frame.getContentPane().add(btnAddCategory);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(161, 190, 255, 178);
		frame.getContentPane().add(scrollPane);
		categoriesTable = new JTable(){
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
	    scrollPane.setViewportView(categoriesTable);
	    categoriesTable.setModel(DbUtils.resultSetToTableModel(db.selectAll("Categories")));
		db.closeConnection();
	    
	    JButton btnDeleteCategory = new JButton("Delete Category");
	    btnDeleteCategory.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		deleteCategory();
	    	}
	    });
	    btnDeleteCategory.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
	    btnDeleteCategory.setBounds(227, 379, 142, 40);
	    frame.getContentPane().add(btnDeleteCategory);
		
		
		
	}
	public static boolean isAlpha(String name) {
	    return name.matches("[a-zA-Z]+");
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
	//function for showing messages
		private static void showMessage(String infoMessage,String titleBar)
		{
			JOptionPane.showMessageDialog(null,infoMessage,titleBar,JOptionPane.INFORMATION_MESSAGE);
		}
	//delete categorybtn
	private static void deleteCategory()
	{
		try {
			DefaultTableModel model = (DefaultTableModel) categoriesTable.getModel();
			//get selected row index
			int selectedRowIndex = categoriesTable.getSelectedRow();
			//gets id value at the selected row 
			String name= (String) categoriesTable.getModel().getValueAt(selectedRowIndex, 0);
			/*search orderdetails table to check if there is products
			 * of the same id before deleting
			 */
			int productsAmount = db.numberOfCategriesInProductsTable(name);
			if(productsAmount>0)
				showMessage("Can't remove category, it appears in products table","Remove failed");
			else
			if(productsAmount==0)//if product doesn't appear in products table
			{			
				if(db.removeCategory(name))
					model.removeRow(selectedRowIndex);//removes row from table
				else
					showMessage("Couldn't remove category,failure in database", "Error");
			}
			else//if there were sql Exception
				showMessage("Couldn't remove category,failure in database","Error");
		}catch(ArrayIndexOutOfBoundsException e)
		{
			showMessage("Please select a row first or refresh the table.","Select A Row");
		}
	}
	
}
