package InvetoRestProject.classes;

//Order details class
public class OrderDetails {

	private int productId;
    private int orderId ;
    private String productName;
    private int quantity;//quantity of each product in order
    private int added=0;//takes only 1 or 0 , 0 is not added to products 1 is added
    
    public OrderDetails()
    {
    	
    }
    //constructor for inserting
	public OrderDetails(int productId, int orderId, int quantity,String productName) {
		
		this.productId = productId;
		this.orderId = orderId;
		this.quantity = quantity;
		this.productName=productName;
		
	}
	//constructor for selecting
	public OrderDetails(int productId, int orderId, int quantity,String productName,int added) {
		
		this.productId = productId;
		this.orderId = orderId;
		this.quantity = quantity;
		this.productName=productName;
		this.added = added;
	}
	public int getAdded()
	{
		return added;
	}
	public void setAdded(int added)
	{
		this.added=added;
	}
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
    
}
