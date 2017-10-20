package bean;

public class Publisher {
	private String publisherName;
	private String address;
	private String phoneNum;
	
	public Publisher() {
		super();
	}

	public Publisher(String publisherName, String address, String phoneNum) {
		super();
		this.publisherName = publisherName;
		this.address = address;
		this.phoneNum = phoneNum;
	}

	public String getPublisherName() {
		return publisherName;
	}
	
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
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
		return "Publisher [publisherName=" + publisherName + ", address=" + address + ", phoneNum=" + phoneNum + "]";
	}
}
