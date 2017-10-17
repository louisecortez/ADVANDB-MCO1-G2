package bean;

public class Book {
	private int bookID;
	private String bookTitle;
	private String publisherName;
	
	public int getBookID() {
		return bookID;
	}
	
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	
	public String getBookTitle() {
		return bookTitle;
	}
	
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	
	public String getPublisherName() {
		return publisherName;
	}
	
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	@Override
	public String toString() {
		return "Book [bookID=" + bookID + ", bookTitle=" + bookTitle + ", publisherName=" + publisherName + "]";
	}
}
