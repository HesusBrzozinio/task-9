package model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

public class Book implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Date year;
	private String title;
	private String publisher;
	private String author;
	private BigDecimal price;

	public Book() {

	}

	public Book(final int id, final Date year, final String title,
			final String publisher, final String author, final BigDecimal price) {
		this.id = id;
		this.year = year;
		this.title = title;
		this.publisher = publisher;
		this.author = author;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public Date getYear() {
		return year;
	}

	public String getTitle() {
		return title;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getAuthor() {
		return author;
	}

	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", year=" + year + ", title=" + title
				+ ", publisher=" + publisher + ", author=" + author
				+ ", price=" + price + "]";
	}

}
