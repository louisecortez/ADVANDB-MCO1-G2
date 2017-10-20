package bean;

public class Borrower {
	private int cardNo;
	private String lastName;
	private String firstName;
	private String address;
	private String phoneNum;
	
	public Borrower() {
		super();
	}

	public Borrower(int cardNo, String lastName, String firstName, String address, String phoneNum) {
		super();
		this.cardNo = cardNo;
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.phoneNum = phoneNum;
	}

	public int getCardNo() {
		return cardNo;
	}
	
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
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
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhoneNum() {
		return phoneNum;
	}
	
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Override
	public String toString() {
		return "Borrower [cardNo=" + cardNo + ", lastName=" + lastName + ", firstName=" + firstName + ", address="
				+ address + ", phoneNum=" + phoneNum + "]";
	}
}
