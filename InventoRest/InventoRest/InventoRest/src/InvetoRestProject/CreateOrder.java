
package InvetoRestProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import InvetoRestProject.classes.Order;
import InvetoRestProject.classes.OrderDetails;
import InvetoRestProject.classes.SendEmail;
import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;

import javax.swing.JTable;

import javax.swing.JButton;
import java.awt.event.ActionListener;

import java.util.Vector;
import java.awt.event.ActionEvent;

//Create Order window form
public class CreateOrder {

	private JFrame frame;
	private JTextField productQuantityField;
	private JButton btnSaveOrder;
	private JTable productsTable;
	private String providerName;

	private SqliteConnection db;
	private JTable orderListTable;
	private DefaultTableModel model;
	private Order order;
	private String userType;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		String providerName = args[0];
		String userId= args[1];
		String userType=args[2];
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateOrder window = new CreateOrder(providerName,userId,userType);
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
	public CreateOrder(String providerName,String userId,String userType) {
		this.providerName=providerName;
		this.userType = userType;
		this.db = new SqliteConnection();
		this.order = new Order(providerName,userId);
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
		
		 model = new DefaultTableModel(0,0);
		 String header[] = new String[] { "id","ProductName","Quantity"};
		 model.setColumnIdentifiers(header);
		 orderListTable=new JTable(){
		        private static final long serialVersionUID = 1L;

		        public boolean isCellEditable(int row, int column) {                
		                return false;               
		        };
		    };
		 orderListTable.setModel(model);
		 JScrollPane scrollPaneOrderList = new JScrollPane();
		 scrollPaneOrderList.setBounds(85, 180, 179, 203);
		 frame.getContentPane().add(scrollPaneOrderList);
		 scrollPaneOrderList.setViewportView(orderListTable);
         
         
         
		JLabel lblCreateOrder = new JLabel("Create Order for "+providerName);
		lblCreateOrder.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateOrder.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		lblCreateOrder.setBounds(211, 29, 280, 32);
		frame.getContentPane().add(lblCreateOrder);
		
		JLabel lblNewLabel = new JLabel("Product Quantity");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		lblNewLabel.setBounds(516, 100, 115, 20);
		frame.getContentPane().add(lblNewLabel);
		
		productQuantityField = new JTextField();
		productQuantityField.setBounds(641, 102, 86, 20);
		frame.getContentPane().add(productQuantityField);
		productQuantityField.setColumns(10);
		
		JScrollPane scrollPaneProducts = new JScrollPane();
		scrollPaneProducts.setBounds(608, 180, 163, 203);
		frame.getContentPane().add(scrollPaneProducts);
		productsTable = new JTable(){
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
		productsTable.setModel(DbUtils.resultSetToTableModel(db.selectProducts()));
		db.closeConnection();
		
		scrollPaneProducts.setViewportView(productsTable);
		
		JLabel lblOrder = new JLabel("Order");
		lblOrder.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		lblOrder.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrder.setBounds(148, 141, 46, 20);
		frame.getContentPane().add(lblOrder);

		
		JButton btnAddProductToOrder = new JButton("Add Product to Order");
		btnAddProductToOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				 //in case of putting characters into quantity field  
	            if(productQuantityField.getText().isEmpty())
	            	showMessage("Please fill quantity text field","Empty Fields");
				else
					if (!(productQuantityField.getText().matches("[0-9]+"))||Integer.parseInt(productQuantityField.getText())<=0)
	                	showMessage("Only positive and above zero numbers allowed in Product Quantity field!","Positive Numbers");	
	            else
	            {
	                try
	                {
	                	//get the selected row
	                	int selectedRowIndex = productsTable.getSelectedRow();
	                	//quantity of the same item that we want to add to order
	                	int quantity = Integer.parseInt(productQuantityField.getText());
	                	//product id to add to order
	                    int productId=(int) productsTable.getModel().getValueAt(selectedRowIndex, 0);
	                    String productName=(String) productsTable.getModel().getValueAt(selectedRowIndex, 1);
	                    //add product to order
	                    OrderDetails od = new OrderDetails(productId, order.getId(), quantity,productName);
	                    boolean productFound = false;//to check if we already have the product in orders list
	                    //move on the list to check if the product name already exists
	                    for(OrderDetails ob : order.getProductsArray())
	                     {
	                       //check if the product name already exists
	                       if (ob.getProductName().equals(productName))
	                        {
	                          //if found then add product quantity to product in orderdetails list
	                    	   int overall = ob.getQuantity()+quantity;
	                    	   ob.setQuantity(overall);
	                           productFound = true;
	                           //look the order list table to change quantity
	                           for(int i=0;i<orderListTable.getRowCount();i++)
	                           {
	                        	   int id = (int) orderListTable.getModel().getValueAt(i,0);
	                        	   if(id==productId)
	                        	   {
	                        		   orderListTable.getModel().setValueAt(overall, i, 2);
	                        		   break;
	                        	   }
	                           }
	                           break;
	                        }
	                      }

	                      //in case of product doesn't exist in arraylist
	                      if (!productFound)
	                      {
	                    	  //add product to order arraylist
	                    	  order.AddProduct(od);
	                    	  
	                    	  //add product with quantity into listbox of order products
	                    	  Vector<Object> data = new Vector<Object>();
	                    	  data.add(productId);
	                    	  data.add(productName);
	                    	  data.add(quantity);
	                    	  model.addRow(data);
	                      }
	                }
	                catch (ArrayIndexOutOfBoundsException e)
	                {
	                	showMessage("Please select a row first or refresh the table.","Select A Row");
	                }

	            }
	           
				
			}
		});
		btnAddProductToOrder.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		btnAddProductToOrder.setBounds(608, 399, 163, 23);
		frame.getContentPane().add(btnAddProductToOrder);
		
		JButton btnFinishOrder = new JButton("Finish Order and Send Now");
		
		
		btnFinishOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean flag = db.insertOrder(order);
				if(order.getProductsArray().size()<=0)
					showMessage("Can't save or send an empty order!","Error");
				else
					if(flag)
					{
						int id = db.lastOrderId();
		                for(OrderDetails od : order.getProductsArray())
		                {
		                    od.setOrderId(id);
		                    db.insertOrderDetails(od);
		                }
		                showMessage("Order saved successfuly!","Success");
		                
		                String[] s= new String[3];
		                s[0] = db.providerEmail(providerName);//saves provider's email
		                if(s[0].equals(""))
		                	showMessage("Order wasn't sent succesfully","Success");
		                else
		                {
		                	SaveAsPdfListener pdf=new SaveAsPdfListener(orderListTable,"Order");
		                	pdf.actionPerformed(arg0);
		                	
		                	s[1]="Arabesq";//saves name of the restaurant
		                	s[2]=pdf.getPdfName();//saves pdf name
		                	
		                	SendEmail.main(s);
		                }
		                frame.dispose();
					}
					else
						showMessage("Order not added nor sent!","Error");
			}
		});
		btnFinishOrder.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		btnFinishOrder.setBounds(162, 465, 209, 32);
		frame.getContentPane().add(btnFinishOrder);
		
		JLabel lblOr = new JLabel("or");
		lblOr.setHorizontalAlignment(SwingConstants.CENTER);
		lblOr.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		lblOr.setBounds(381, 473, 16, 14);
		frame.getContentPane().add(lblOr);
		
		btnSaveOrder = new JButton("Save Order for Later");
		btnSaveOrder.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		btnSaveOrder.setBounds(407, 470, 149, 23);
		frame.getContentPane().add(btnSaveOrder);
		
		//removes product from order list by click
		JButton btnRemoveProductFrom = new JButton("Remove Product From Order");
		btnRemoveProductFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowIndex = orderListTable.getSelectedRow();
            	
            	//product id to remove  from order
                int productId=(int) orderListTable.getModel().getValueAt(selectedRowIndex, 0);
                //looking for the product id that is similar
                for(OrderDetails ob : order.getProductsArray())
                {
                  if (ob.getProductId()==(productId))
                   {
                	  
                	  for(int i=0;i<orderListTable.getRowCount();i++)
                      {
                   	   int id = (int) orderListTable.getModel().getValueAt(i,0);
                   	   if(id==productId)
                   		   {
                   		   //remove row from table
	                   		   	model.removeRow(i);
	                   		   	break;
                   		   }
                   	   
                      }
                	  //remove row from array
                	  order.getProductsArray().remove(ob);
                      break;
                   }
                 }
                
				
			}
		});
		btnRemoveProductFrom.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
		btnRemoveProductFrom.setBounds(34, 395, 286, 37);
		frame.getContentPane().add(btnRemoveProductFrom);
		btnSaveOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean flag = db.insertOrder(order);
				if(order.getProductsArray().size()<=0)
					showMessage("Can't save or send an empty order!","Error");
				else
					if(flag)
					{
						int id = db.lastOrderId();
		                for(OrderDetails od : order.getProductsArray())
		                {
		                    od.setOrderId(id);
		                    db.insertOrderDetails(od);
		                }
		                showMessage("Order saved successfuly!","Success");
		                frame.dispose();
					}
					else
						showMessage("Order not Saved!","Error");
			}
		});
		
		if(userType.equals("w"))
		{
			btnFinishOrder.setVisible(false);
			lblOr.setVisible(false);
		}
	}

	//function for showing messages
		public static void showMessage(String infoMessage,String titleBar)
		{
			JOptionPane.showMessageDialog(null,infoMessage,titleBar,JOptionPane.INFORMATION_MESSAGE);
		}
}
