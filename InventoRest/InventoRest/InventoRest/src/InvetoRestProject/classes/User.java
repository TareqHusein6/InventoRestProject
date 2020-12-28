package InvetoRestProject.classes;

//abstract user class to inherit worker and manager
public abstract class User {

	private String id;
	private String firstname;
	private String lastname;
	private String password;
	
	public User(String id, String firstname, String lastname, String password) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
