package uploads.models;





public class Members {
	private transient int id;
	private String name;
	private String gender;
	public Members( String name, String gender) {
		super();
		
		this.name = name;
		this.gender = gender;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	@Override
	public String toString() {
		return "Members [id=" + id + ", name=" + name + ", gender=" + gender + "]";
	}
	public Members(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
