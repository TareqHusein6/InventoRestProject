package InvetoRestProject;

//sqlite connection and queries class
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import InvetoRestProject.classes.*;

public class SqliteConnection {
	Connection conn=null;
	private Connection dbConnector() {
		try {
			Class.forName("org.sqlite.JDBC");
			String dbpath = "jdbc:sqlite:"+System.getProperty("user.dir")+"\\src\\InvetoRestProject\\InventoRestData.db";
			conn= DriverManager.getConnection(dbpath);
			return conn;
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		
	}
	public void closeConnection()
	{
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 public Boolean insertUser(User user)//m = manager or w= worker
	 {
	        String sql = "INSERT INTO users(id,firstName,lastName,email,password,mw) VALUES(?,?,?,?,?,?)";
	 
	        try ( Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, user.getId());
	            pstmt.setString(2, user.getFirstname());
	            pstmt.setString(3, user.getLastname());
	            if(user instanceof Manager)
	            	{
	            		pstmt.setString(4, ((Manager)user).getEmail());
	            		pstmt.setString(6, "m");
	            	}
	            else
	            	{
	            	pstmt.setString(4, "");
            		pstmt.setString(6, "w");
	            	}
	            pstmt.setString(5, user.getPassword());

	            pstmt.executeUpdate();
	            
	            conn.close();
	            pstmt.close();
	            
	            return true;
	        } catch (SQLException e) {
	        	e.printStackTrace();
	            return false;
	        }
	        
	    }
	 //removes user
	 public boolean removeUser(String id)
	 {
		 String query = "DELETE FROM Users WHERE id=?";
		 try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(query)) {
			 		//sets the passed parameter instead of ?
			 		pstmt.setString(1, id);
			 		//executes the delete statement
			 		pstmt.executeUpdate();
			 		
			 		conn.close();
		            pstmt.close();
			 		return true;
			 		
		 }catch (SQLException e) {
			 e.printStackTrace();
	            return false;
	        }
	 }
	
	 //checks if user appears
	 public int checkUser(String id,String pass)
	 {
		 String query="select * from users where id=? and password=? ";
		 try(Connection conn=dbConnector();
			PreparedStatement pstmt=conn.prepareStatement(query)){
				pstmt.setString(1, id);
				pstmt.setString(2, pass);
				ResultSet rs=pstmt.executeQuery();//excuting query
				int count=0;//counting rows 
				while(rs.next())
				{
					count++;	
				}
				conn.close();
				pstmt.close();
				rs.close();

				return count;
		 }
		catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	
	 }
	 //checks if user is manager or worker
	 public  Object managerOrUser(String id) {
		 String query="select mw from users where id=?";
		 try(Connection conn=dbConnector();
					PreparedStatement pstmt=conn.prepareStatement(query)){
			pstmt.setString(1,id);
			ResultSet rs=pstmt.executeQuery();
			Object obj=rs.getObject(1);//returns the column number 1 as object
			
			conn.close();
			pstmt.close();
			rs.close();
			
			return obj;
		 }catch(SQLException e) {
			 e.printStackTrace();
				return null;
			}
	 }
	 //edits user
	 public  boolean editUser(User user,String id) {
		 String query = "UPDATE users SET id = ? , firstname = ? , lastname = ? , email = ? , password = ? , mw = ? WHERE id=?";
		  try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(query)) {
	            pstmt.setString(1, user.getId());
	            pstmt.setString(2, user.getFirstname());
	            pstmt.setString(3, user.getLastname());
	            if(user instanceof Manager)
	            	{
	            		pstmt.setString(4, ((Manager)user).getEmail());
	            		pstmt.setString(6, "m");
	            	}
	            else
	            	{
		            	pstmt.setString(4, "");
		            	pstmt.setString(6, "w");
	            	}
	            pstmt.setString(5, user.getPassword());
	            pstmt.setString(7, id);
	            
	            pstmt.executeUpdate();
	            
	            conn.close();
	            pstmt.close();
	           
	            return true;
	        } catch (SQLException e) {
	        	e.printStackTrace();
	            return false;
	        }
	        
	 }
	 public  boolean insertProvider(Provider p)
	 {
		 String sql = "INSERT INTO providers(name,email,phone) VALUES(?,?,?)";
		 
	        try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, p.getName());
	            pstmt.setString(2, p.getEmail());
	            pstmt.setString(3, p.getPhone());
	            
	            pstmt.executeUpdate();
	            
	            conn.close();
				pstmt.close();
				
				return true;
	        
	        } catch (SQLException e) {
	        	e.printStackTrace();
	            return false;
	        }
		 
	 }
	 public  boolean removeProvider(String name)
	 {
		 String query = "DELETE FROM Providers WHERE name=?";
		 try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(query)) {
			 		//sets the passed parameter instead of ?
			 		pstmt.setString(1, name);
			 		//executes the delete statement
			 		pstmt.executeUpdate();
			 		
			 		conn.close();
					pstmt.close();
					
			 		return true;
			 		
		 }catch (SQLException e) {
			 e.printStackTrace();
	            return false;
	        }
	 }
	 /*
	  * @param name is the past name before change
	  * */
	 public boolean editProvider(Provider p,String name)
	 {
		 String query = "UPDATE providers SET name = ? , email = ? , phone = ? WHERE name = ?";
		  try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(query)) {
	            pstmt.setString(1, p.getName());
	            pstmt.setString(2, p.getEmail());
	            pstmt.setString(3, p.getPhone());
	            pstmt.setString(4,name);
	            
	            pstmt.executeUpdate();
	            
	            conn.close();
				pstmt.close();
				
	            return true;
	        } catch (SQLException e) {
	        	e.printStackTrace();
	            return false;
	        }
	 }
	 /*
	  * returns true if category inserted successfully into db
	  */
	 public  boolean insertCategory(String cn)
	 {
		 String sql = "INSERT INTO Categories(name) VALUES(?)";
		 
	        try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, cn);
	            pstmt.executeUpdate();
	            
	            conn.close();
				pstmt.close();
				
	            return true;
	        
	        } catch (SQLException e) {
	        	e.printStackTrace();
	            return false;
	        }
	 }
	 /*
	  * returns strings array of providers Names to use in add product
	  * */
	 
	 public  String[] providersNames()
	 {
		 //saves names returned
		 List<String> names=new ArrayList<String>();
		 
		 String query = "SELECT name from Providers";
		  try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(query)) {
	            ResultSet rs=pstmt.executeQuery();
	            
	            //move on the table of objects
	            while (rs.next()) {
	            	//add each name to the arraylist
	            	names.add(rs.getString("name"));

	            }
	       
	            //return the names as array
	            String[] namesArr = new String[names.size()];
	            namesArr = names.toArray(namesArr);
	            
	            conn.close();
				pstmt.close();
				
	            return namesArr;
	            
	        } catch (SQLException e) {
	        	e.printStackTrace();
	            return null;
	        }
	 }
	 /**
	  * 
	  * @return categories names to use in add product combobox
	  */
	 public  String[] categoriesNames()
	 {
		 //saves names returned
		 List<String> names=new ArrayList<String>();
		 
		 String query = "SELECT name from Categories";
		  try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(query)) {
	            ResultSet rs=pstmt.executeQuery();
	            
	            //move on the table of objects
	            while (rs.next()) {
	            	//add each name to the arraylist
	            	names.add(rs.getString("name"));

	            }
	       
	            //return the names as array
	            String[] namesArr = new String[names.size()];
	            namesArr = names.toArray(namesArr);
	            
	            conn.close();
				pstmt.close();
				
	            return namesArr;
	            
	        } catch (SQLException e) {
	        	e.printStackTrace();
	            return null;
	        }
	 }
	 /**
	  * inserts product into database
	  * @param p product
	  * @return bool
	  */
	 public  boolean insertProduct(Product p)
	 {
		 String sql = "INSERT INTO Products(name,discription,quantity,price,expirationDate,buyDate,categoryName,providerName,workerId) VALUES(?,?,?,?,?,?,?,?,?)";
		 
	        try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, p.getName());
	            pstmt.setString(2, p.getDiscription());
	            pstmt.setLong(3, p.getQuantity());
	            pstmt.setDouble(4, p.getPrice());
	            pstmt.setString(5, p.getExpirationDate().toString());
	            pstmt.setString(6, p.getBuyDate().toString());
	            pstmt.setString(7, p.getCategoryName());
	            pstmt.setString(8, p.getProviderName());
	            pstmt.setString(9, p.getWorkerId());

	            
	            pstmt.executeUpdate();
	            
	            conn.close();
				pstmt.close();
				
	            return true;
	        
	        } catch (SQLException e) {
	        	e.printStackTrace();
	            return false;
	        }
	 }
	 /*
	  * @param name is the past name before change
	  * */
	 public  boolean editProduct(Product p)
	 {
		 String query = "UPDATE Products SET name = ? , discription = ? , quantity = ? ,"
		 		+ " price = ? , expirationDate = ? , buyDate = ? , categoryName = ? , providerName = ? , workerId = ? WHERE id = ?";
		  try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(query)) {
	            pstmt.setString(1, p.getName());
	            pstmt.setString(2, p.getDiscription());
	            pstmt.setLong(3, p.getQuantity());
	            pstmt.setDouble(4,p.getPrice());
	            pstmt.setString(5,p.getExpirationDate());
	            pstmt.setString(6,p.getBuyDate());
	            pstmt.setString(7,p.getCategoryName());
	            pstmt.setString(8,p.getProviderName());
	            pstmt.setString(9,p.getWorkerId());
	            pstmt.setLong(10,p.getId());

	            pstmt.executeUpdate();
	            
	            conn.close();
	         	pstmt.close();
	         	
	            return true;
	        } catch (SQLException e) {
	        	e.printStackTrace();
	        	
	            return false;
	        }
	 }
	 //remove product from database
	 public  boolean removeProduct(int id)
	 {
		 String query = "DELETE FROM Products WHERE id=?";
		 try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(query)) {
			 		//sets the passed parameter instead of ?
			 		pstmt.setInt(1, id);
			 		//executes the delete statement
			 		pstmt.executeUpdate();
			 		
			 		conn.close();
					pstmt.close();
					
			 		return true;
			 		
		 }catch (SQLException e) {
			 e.printStackTrace();
			 
	            return false;
	        }
	 }
	 //remove order and order details of the same order
	 public boolean removeOrder(int id)
	 {
		 //remove order details and make sure it's deleted
		 if(removeOrderDetails(id))
		 {	 
			 String query = "DELETE FROM Orders WHERE id=?";
			 try (Connection conn = dbConnector();
		                PreparedStatement pstmt = conn.prepareStatement(query)) {
				 		//sets the passed parameter instead of ?
				 		pstmt.setInt(1, id);
				 		//executes the delete statement
				 		pstmt.executeUpdate();
				 		
				 		conn.close();
						pstmt.close();
						
				 		return true;
				 		
			 }catch (SQLException e) {
				 e.printStackTrace();
				 
		            return false;
		        }
		 }
		 else
			 return false;
	 }
	 //removes order details
	 public boolean removeOrderDetails(int id)
	 {
		 String query = "DELETE FROM OrderDetails WHERE orderId=?";
		 try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(query)) {
			 		//sets the passed parameter instead of ?
			 		pstmt.setInt(1, id);
			 		//executes the delete statement
			 		pstmt.executeUpdate();
			 		
			 		 conn.close();
						pstmt.close();
			 		return true;
			 		
		 }catch (SQLException e) {
			 e.printStackTrace();
			 	
	            return false;
	        }
	 }
	 public ResultSet selectAll(String table){
	        String query = "select * from "+table;
	        
	        try {
	            conn = dbConnector();
	            Statement pstmt  = conn.createStatement();
	        	ResultSet rs = pstmt.executeQuery(query);
	        	
	            return rs;
	            
	        } catch (SQLException e) {
	        	e.printStackTrace();
	            
	            return null;
	        }
	    }
	 //select provider names to display in order tab
	 public ResultSet selectProvidersNames()
	 {
		 String query = "select name from Providers";
	        
	        try {
	        	conn = dbConnector();
	            Statement pstmt  = conn.createStatement();
	        	ResultSet rs = pstmt.executeQuery(query);
	        	 
	            return rs;
	            
	        } catch (SQLException e) {
	        	e.printStackTrace();
	            
	            return null;
	        }
	 }
	 public ResultSet selectOrderDetails(int orderId)
	 {
		 String query = "select * from OrderDetails WHERE orderId="+orderId;
     
	     try {
	     	conn = dbConnector();
	        Statement pstmt  = conn.createStatement();
	     	ResultSet rs = pstmt.executeQuery(query);
	     	 
	         return rs;
	         
	     } catch (SQLException e) {
	    	 e.printStackTrace();
	         
	         return null;
	     }
		 
	 }
	 /*
	  * returns the result set of the products to show in create order form product table
	  */
	 public ResultSet selectProducts()
	 {
		 String query="select id, name, quantity from Products";
		 try {
		     	conn = dbConnector();
		        Statement pstmt  = conn.createStatement();
		     	ResultSet rs = pstmt.executeQuery(query);
		     	
		        return rs;
		         
		     } catch (SQLException e) {
		    	 e.printStackTrace();
		         
		         return null;
		     }
	 }
	 /**
	  * insert order into database
	  */
	 public boolean insertOrder(Order o)
	 {
		 String sql = "INSERT INTO Orders(date,providerName,workerId,totalProducts) VALUES(?,?,?,?)";
		 
	     try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, o.getOrderDate());
	            pstmt.setString(2, o.getProviderName());
	            pstmt.setString(3, o.getWorkerId());
	            pstmt.setInt(4, o.getTotalProducts());
	            pstmt.executeUpdate();
	           
	            conn.close();
				pstmt.close();
				
	            return true;
	        } catch (SQLException e) {
	        	e.printStackTrace();
	        	
	            return false;
	        }
				 
	 }
	 /*
	  * gets the most least added orderId from the database 
	  */
	 public int lastOrderId()
	 {
		 String query="SELECT MAX(id) AS LAST FROM Orders";
		 int orderId=-1;
		 
		 try(Connection conn=dbConnector();
					PreparedStatement pstmt=conn.prepareStatement(query)){
			ResultSet rs=pstmt.executeQuery();
			 //move on the table of objects
            while (rs.next()) {
            	orderId=rs.getInt(1);
            }
            conn.close();
			pstmt.close();
			rs.close();
			return orderId;
		 }catch(SQLException e) {
			 e.printStackTrace();
				
				//returns -1 
				return orderId;
			}
	 }
	 /*
	  * inserts order details for a single item
	  */
	 public boolean insertOrderDetails(OrderDetails od)
	 {
		 String sql = "INSERT INTO OrderDetails(productName,quantity,productId,orderId,added) VALUES(?,?,?,?,?)";
		 
	     try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, od.getProductName());
	            pstmt.setInt(2, od.getQuantity());
	            pstmt.setInt(3, od.getProductId());
	            pstmt.setInt(4, od.getOrderId());
	            pstmt.setInt(5, od.getAdded());//added value

	            pstmt.executeUpdate();
	            
	            conn.close();
				pstmt.close();
	            
				return true;
	        
	        } catch (SQLException e) {
	        	e.printStackTrace();
	        	
	            return false;
	        }
	 }
	 /*
	  * select provider's email
	  */
	 public String providerEmail(String providerName)
	 {
		 String query="select email from Providers where name=?";
		 String providerEmail="";
		 
		 try(Connection conn=dbConnector();
					PreparedStatement pstmt=conn.prepareStatement(query)){
			 pstmt.setString(1, providerName);
			ResultSet rs=pstmt.executeQuery();
			 //move on the table of objects
            while (rs.next()) {
            	providerEmail=rs.getString(1);
            }
            conn.close();
			pstmt.close();
			rs.close();
			return providerEmail;
		 }catch(SQLException e) {
			 e.printStackTrace();
				
				//returns -1 
				return providerEmail;
			}
	 }
	 /*
	  * returns user name
	  */
	 public String[] userName(String id)
	 {
		 String query="select firstname,lastname from Users where id=?";
		 String[] fullname=new String[2];
		 
		 try(Connection conn=dbConnector();
					PreparedStatement pstmt=conn.prepareStatement(query)){
			 pstmt.setString(1, id);
			ResultSet rs=pstmt.executeQuery();
			 //move on the table of objects
            while (rs.next()) {
            	fullname[0]=rs.getString(1);
            	fullname[1]=rs.getString(2);
            }
            conn.close();
			pstmt.close();
			rs.close();
			return fullname;
		 }catch(SQLException e) {
			 e.printStackTrace();
				
				//returns -1 
				return null;
			}
	 }
	 /*
	  * removes 1 product from order
	  */
	 public boolean removeOrderProduct(int productId,int orderId)
	 {
		 String query = "DELETE FROM OrderDetails WHERE productId=? AND orderId=?";
		 try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(query)) {
			 		//sets the passed parameter instead of ?
			 		pstmt.setInt(1, productId);
			 		pstmt.setInt(2, orderId);
			 		//executes the delete statement
			 		pstmt.executeUpdate();
			 		
			 		 conn.close();
					pstmt.close();
			 		return true;
			 		
		 }catch (SQLException e) {
			 e.printStackTrace();
			 	
	            return false;
	        }
	 }
	 /*
	  *Returns arrayList of the order details 
	  */
	 public List<OrderDetails> orderDetailsArray(int id)
	 {
		 try {
		 ResultSet rs = this.selectOrderDetails(id);
		 if(rs==null)
			 return null;
		 List<OrderDetails> ods=new ArrayList<OrderDetails>();
		 
		 while (rs.next()) {
			 
			OrderDetails od = new OrderDetails();
         	od.setProductName(rs.getString(1));
         	od.setQuantity(rs.getInt(2));
         	od.setProductId(rs.getInt(3));
         	od.setOrderId(rs.getInt(4));
         	od.setAdded(rs.getInt(5));
         	ods.add(od);
         }
         rs.close();
         
		 return ods;
		 
	 }catch (SQLException e) {
		 e.printStackTrace();
		 	
            return null;
        }
	 }
	 /*
	  * updates all the products in products for the new Order
	  * 
	  */
	 public boolean updateProductsQuantity(OrderDetails od)
	 { 
		 String query = "UPDATE Products SET quantity = quantity + ? "
			 		+ "WHERE id = ?";
		 try (Connection conn = dbConnector();
		       PreparedStatement pstmt = conn.prepareStatement(query)){
			 
				pstmt.setInt(1, od.getQuantity());
		        pstmt.setInt(2, od.getProductId());
				  
		         //executes the update statement
			 	pstmt.executeUpdate();
			 		
				conn.close();
				pstmt.close();
				
				return true;
		 }catch (SQLException e) {
			 e.printStackTrace();
			 	
	            return false;
	        }
	 }
	 /*
	  * updates added value in orderDetails table
	  */
	 public boolean updateAdded(OrderDetails od)
	 {
		 String query = "UPDATE OrderDetails SET added = 1 "
			 		+ "WHERE productId = ? AND orderId = ?";
		 try (Connection conn = dbConnector();
		       PreparedStatement pstmt = conn.prepareStatement(query)){
			 
				pstmt.setInt(1, od.getProductId());
		        pstmt.setInt(2, od.getOrderId());
				  
		         //executes the update statement
			 	pstmt.executeUpdate();
			 		
				conn.close();
				pstmt.close();
				
				return true;
		 }catch (SQLException e) {
			 e.printStackTrace();
			 	
	            return false;
	        }
	 }
	 /*
	  * search in products table 
	  * @keyWord - search value
	  */
	 public ResultSet searchProducts(String keyWord)
	 {
		 String query="select * from Products WHERE name = ?";
		 try{
			 Connection conn=dbConnector();
			 PreparedStatement pstmt=conn.prepareStatement(query);
			 
			 pstmt.setString(1, keyWord);

			 ResultSet rs = pstmt.executeQuery();
		     	
		      return rs;
		         
		     } catch (SQLException e) {
		    	 e.printStackTrace();
		         
		         return null;
		     }
	 }
	 /*
	  * search in providers table
	  */
	 public ResultSet searchProviders(String keyWord)
	 {
		 String query="select * from Providers WHERE name = ?";
		 try{
			 Connection conn=dbConnector();
			 PreparedStatement pstmt=conn.prepareStatement(query);
			 
			 pstmt.setString(1, keyWord);

			 ResultSet rs = pstmt.executeQuery();
		     	
		      return rs;
		         
		     } catch (SQLException e) {
		    	 e.printStackTrace();
		         
		         return null;
		     }
	 }
	 /*
	  * search in users table
	  */
	 public ResultSet searchUsers(String keyWord)
	 {
		 String query="select * from Users WHERE firstname = ?";
		 try{
			 Connection conn=dbConnector();
			 PreparedStatement pstmt=conn.prepareStatement(query);
			 
			 pstmt.setString(1, keyWord);

			 ResultSet rs = pstmt.executeQuery();
		     	
		      return rs;
		         
		     } catch (SQLException e) {
		    	 e.printStackTrace();
		         
		         return null;
		     }
	 }
	 /*
	  * search in orders table
	  */
	 public ResultSet searchOrders(String fromDate,String toDate)
	 {
		 String query="select * from Orders WHERE date BETWEEN"
		 		+ " ? AND ?";
		 try{
			 Connection conn=dbConnector();
			 PreparedStatement pstmt=conn.prepareStatement(query);
			 
			 pstmt.setString(1, fromDate);
			 pstmt.setString(2, toDate);

			 ResultSet rs = pstmt.executeQuery();
		     	
		      return rs;
		         
		     } catch (SQLException e) {
		    	 e.printStackTrace();
		         
		         return null;
		     }
	 }
	 /*
	  * search products in order details table
	  * return number of rows 
	  */
	 public int numberOfProductsInOrderDetails(int productId)
	 {
		 String query="select * from OrderDetails WHERE productId = ?";
		 int count=0;
		 try{
				 Connection conn=dbConnector();
				 PreparedStatement pstmt=conn.prepareStatement(query);
				 
				 pstmt.setInt(1, productId);
				 

				 ResultSet rs = pstmt.executeQuery();
				 while(rs.next())
					{
						count++;	
					}
					conn.close();
					pstmt.close();
					rs.close();
					
			      return count;
			         
			     } catch (SQLException e) {
			    	 e.printStackTrace();
			         
			         return -1;
			     }
	 }
	 /*
	  * returns number of rows that includes provider's name in given table
	  * @tableName given table name to search in
	  */
	 public int numberOfProvidersInAtable(String providerName,String tableName)
	 {
		 String query="select * from "+tableName+" WHERE providerName = ?";
		 int count=0;
		 try{
				 Connection conn=dbConnector();
				 PreparedStatement pstmt=conn.prepareStatement(query);
				 
				 pstmt.setString(1, providerName);
				 ResultSet rs = pstmt.executeQuery();
				 while(rs.next())
					{
						count++;	
					}
					conn.close();
					pstmt.close();
					rs.close();
					
			      return count;
			         
			     } catch (SQLException e) {
			    	 e.printStackTrace();
			         
			         return -1;
			     }
	 }
	 /*
	  * search orderDetails
	  */
	 public int numberOfCategriesInProductsTable(String categoryName)
	 {
		 String query="select * from Products WHERE categoryName = ?";
		 int count=0;
		 try{
				 Connection conn=dbConnector();
				 PreparedStatement pstmt=conn.prepareStatement(query);
				 
				 pstmt.setString(1, categoryName);
				 

				 ResultSet rs = pstmt.executeQuery();
				 while(rs.next())
					{
						count++;	
					}
					conn.close();
					pstmt.close();
					rs.close();
					
			      return count;
			         
			     } catch (SQLException e) {
			    	 e.printStackTrace();
			         
			         return -1;
			     }
	 }
	 /*
	  * removes category
	  */
	 public boolean removeCategory(String name)
	 {
		 String query = "DELETE FROM Categories WHERE name=?";
		 try (Connection conn = dbConnector();
	                PreparedStatement pstmt = conn.prepareStatement(query)) {
			 		//sets the passed parameter instead of ?
			 		pstmt.setString(1, name);
			 		//executes the delete statement
			 		pstmt.executeUpdate();
			 		
			 		conn.close();
		            pstmt.close();
			 		return true;
			 		
		 }catch (SQLException e) {
			 e.printStackTrace();
	            return false;
	        }
	 }
	 /*
	  * returns number of users appearance in given table
	  */
	 public int numberOfUsersInAtable(String userId,String tableName)
	 {
		 String query="select * from "+tableName+" WHERE workerId = ?";
		 int count=0;
		 try{
				 Connection conn=dbConnector();
				 PreparedStatement pstmt=conn.prepareStatement(query);
				 
				 pstmt.setString(1, userId);
				 ResultSet rs = pstmt.executeQuery();
				 while(rs.next())
					{
						count++;	
					}
					conn.close();
					pstmt.close();
					rs.close();
					
			      return count;
			         
			     } catch (SQLException e) {
			    	 e.printStackTrace();
			         
			         return -1;
			     }
	 }
	 /*
	  * search in OrderDetails table 
	  * @keyWord - search value
	  */
	 public ResultSet searchOrderDetails(String keyWord,int orderId)
	 {
		 String query="select * from OrderDetails WHERE productName = ? AND orderId = ?";
		 try{
			 Connection conn=dbConnector();
			 PreparedStatement pstmt=conn.prepareStatement(query);
			 
			 pstmt.setString(1, keyWord);
			 pstmt.setInt(2, orderId);

			 ResultSet rs = pstmt.executeQuery();
		     	
		      return rs;
		         
		     } catch (SQLException e) {
		    	 e.printStackTrace();
		         
		         return null;
		     }
	 }
}
	 


