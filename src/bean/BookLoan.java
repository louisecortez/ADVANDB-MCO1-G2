package bean;

public class BookLoan {
	private int bookID;
	private int branchID;
	private int cardNo;
	private String dateOut;
	private String dueDate;
	private String dateReturned;
	
	public BookLoan() {
		super();
	}

	public BookLoan(int bookID, int branchID, int cardNo, String dateOut, String dueDate, String dateReturned) {
		super();
		this.bookID = bookID;
		this.branchID = branchID;
		this.cardNo = cardNo;
		this.dateOut = dateOut;
		this.dueDate = dueDate;
		this.dateReturned = dateReturned;
	}

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public int getBranchID() {
		return branchID;
	}

	public void setBranchID(int branchID) {
		this.branchID = branchID;
	}

	public int getCardNo() {
		return cardNo;
	}

	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}

	public String getDateOut() {
		return dateOut;
	}

	public void setDateOut(String dateOut) {
		this.dateOut = dateOut;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getDateReturned() {
		return dateReturned;
	}

	public void setDateReturned(String dateReturned) {
		this.dateReturned = dateReturned;
	}
}
