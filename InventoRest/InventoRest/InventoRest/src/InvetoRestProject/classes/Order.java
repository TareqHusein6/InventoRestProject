package InvetoRestProject.classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//Order Class
public class Order {
	private int id;
	private String orderDate;
	private String providerName;
	private String workerId;
	private ArrayList<OrderDetails> productsArray;
	private int totalProducts;//total products types
	
	/**
	 * constructor for selecting
	 * @param id
	 * @param orderDate
	 * @param providerId
	 * @param workerId
	 * @param products
	 * @param totalProducts
	 */
	public Order(int id, String orderDate, String providerName,String workerId,ArrayList<OrderDetails> products,int totalProducts) {
		
		this.id = id;
		this.orderDate = orderDate;
		this.providerName = providerName;
		this.workerId=workerId;
		this.productsArray = products;
		this.totalProducts=totalProducts;
	}
	/*
	 * constructor for inserting
	 */
	public Order(String providerName,String workerId) {
		this.providerName = providerName;
		this.workerId=workerId;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date orderDate=new Date();
		this.orderDate = sdf.format(orderDate);
	
		this.productsArray = new ArrayList<OrderDetails>();
		this.totalProducts=0;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerId) {
		this.providerName = providerId;
	}
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public ArrayList<OrderDetails> getProductsArray() {
		return productsArray;
	}
	public void setProductsArray(ArrayList<OrderDetails> productsArray) {
		this.productsArray = productsArray;
	}
	public int getTotalProducts() {
		return totalProducts;
	}
	public void setTotalProducts(int totalProducts) {
		this.totalProducts = totalProducts;
	}
	/*
	 * adds products to the products array in an order
	 */
	public void AddProduct(OrderDetails od)
    {
        this.productsArray.add(od);
        this.totalProducts++;
    }
	
	
}
