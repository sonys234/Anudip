package com.bookvault.dto;

public class BookDTO {
	private int bookId;
	private String title;
	private String author;
	private String description;
	private String genre;
	private Double price;
	private int quantity;
	private double rating;
	
	public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
	
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public String getDescription() {
		return description;
	}
	public String getGenre() {
		return genre;
	}
	public Double getPrice() {
		return price;
	}
	public int getQuantity() {
		return quantity;
	}
	public double getRating() {
		return rating;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}

}
