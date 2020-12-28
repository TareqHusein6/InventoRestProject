package InvetoRestProject.classes;

//Product Class
public class Product {
	private int id;
	private String name;
	private String discription;
	private int quantity;
	private double price;
	private String expirationDate;
	private String buyDate;
	private String categoryName;
	private String providerName;
	private String workerId;
	
	
	public Product(int id, String name, String discription, int quantity, double price, String expirationDate,
			String buyDate, String categoryName, String providerName, String workerId) {
		super();
		this.id = id;
		this.name = name;
		this.discription = discription;
		this.quantity = quantity;
		this.price = price;
		this.expirationDate = expirationDate;
		this.buyDate = buyDate;
		this.categoryName = categoryName;
		this.providerName = providerName;
		this.workerId = workerId;
	}
	
	public Product(String name, String discription, int quantity, double price, String expirationDate, String buyDate,
			String categoryName, String providerName, String workerId) {
		super();
		this.name = name;
		this.discription = discription;
		this.quantity = quantity;
		this.price = price;
		this.expirationDate = expirationDate;
		this.buyDate = buyDate;
		this.categoryName = categoryName;
		this.providerName = providerName;
		this.workerId = workerId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	

}
