package InvetoRestProject.classes;

//Manager class
public class Manager extends User {
	private String email;
	public Manager(String id, String firstname, String lastname,String email, String password) {
		super(id, firstname, lastname, password);
		this.email=email;
		// TODO Auto-generated constructor stub
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}
