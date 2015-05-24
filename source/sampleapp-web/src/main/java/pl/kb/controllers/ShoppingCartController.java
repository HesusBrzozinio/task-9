package pl.kb.controllers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.dto.Book;
import model.dto.valueholder.Quantity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.jms.MessageSenderBean;

public class ShoppingCartController  {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory
			.getLogger(ShoppingCartController.class);
	private Map<Book, Quantity> books = new HashMap<Book, Quantity>();
	private List<Entry<Book, Quantity>> entries;
	private BigDecimal totalPrice = BigDecimal.ZERO;

	private int cartItemQuantity;


	private void updateCart(final Book book, final int quantity) {
		if (books.containsKey(book)) {
			final Quantity q = books.get(book);
			int newQuantity = q.getValue() + quantity;
			q.setValue(newQuantity);
			books.put(book, q);
			LOG.info("updating book quantity");
		} else {
			final Quantity q = new Quantity();
			q.setValue(quantity);
			books.put(book, q);
			LOG.info("new book");
		}
	}

	private void calculatePriceAndQuantity() {
		totalPrice = BigDecimal.ZERO;
		cartItemQuantity = 0;
		for (final Book b : books.keySet()) {
			final BigDecimal itemPrice = b.getPrice();
			final Quantity itemQueantity = books.get(b);
			final BigDecimal price = itemPrice.multiply(new BigDecimal(
					itemQueantity.getValue()));

			totalPrice = totalPrice.add(price);
			cartItemQuantity = cartItemQuantity + itemQueantity.getValue();
		}
	}

	public Map<Book, Quantity> getBooks() {
		return books;
	}

	public int getCartItemQuantity() {
		return cartItemQuantity;
	}

	public List<Entry<Book, Quantity>> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry<Book, Quantity>> entries) {
		this.entries = entries;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
}
