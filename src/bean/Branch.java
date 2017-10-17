package bean;

public class Branch {
	private int branchID;
	private String branchName;
	private String address;
	
	public int getBranchID() {
		return branchID;
	}
	
	public void setBranchID(int branchID) {
		this.branchID = branchID;
	}
	
	public String getBranchName() {
		return branchName;
	}
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Branch [branchID=" + branchID + ", branchName=" + branchName + ", address=" + address + "]";
	}
}
