package test_tutorial.model;

import java.util.List;

public class Book {
	/**
	 * 書籍名.
	 */
	private String name;

	/**
	 * 出版社.
	 */
	private String publisher;
	
	/**
	 * 価格.
	 */
	private int price;
	
	/**
	 * 著者・訳者.
	 */
	private List<String> authers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<String> getAuthers() {
		return authers;
	}

	public void setAuthers(List<String> authers) {
		this.authers = authers;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name=").append(name).append(", ")
		  .append("publisher=").append(publisher).append(", ")
		  .append("price=").append(price).append(", ")
		  .append("authers=").append(authers);
		return sb.toString();
	}

}
