package bean;

public class Author {
	private int bookID;
	private String lastName;
	private String firstName;
	
	public Author() {
		super();
	}

	public Author(int bookID, String lastName, String firstName) {
		super();
		this.bookID = bookID;
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public int getBookID() {
		return bookID;
	}
	
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return "Author [bookID=" + bookID + ", lastName=" + lastName + ", firstName=" + firstName + "]";
	}
}
