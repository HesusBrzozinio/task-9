package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import model.dto.Book;

@Stateless
public class BookServiceBean implements BookServiceLocal, BookServiceRemote {

	private List<Book> books;

	@PostConstruct
	private void init() {
		books = new ArrayList<Book>();
		books.add(new Book(1, new Date(), "Kroniki ptaka nakręcacza",
				"czerwony", "Haruki Murakami", new BigDecimal(23.50)));
		books.add(new Book(2, new Date(), "1Q84", "granatowy",
				"Haruki Murakami", new BigDecimal(23.50)));
		books.add(new Book(3, new Date(), "Nagłe pukanie do drzwi", "czarny",
				"Etgar Keret", new BigDecimal(23.50)));
		books.add(new Book(4, new Date(), "Kobiety", "zielony",
				"Charles Bukowski", new BigDecimal(23.50)));
		books.add(new Book(5, new Date(), "Rury", "niebieski", "Etgar Keret",
				new BigDecimal(23.50)));
		books.add(new Book(6, new Date(), "Norwegian Wood", "srebrny",
				"Haruki Murakami", new BigDecimal(23.50)));
		books.add(new Book(7, new Date(), "Cma barowa", "biały",
				"Charles Bukowski", new BigDecimal(23.50)));
		books.add(new Book(8, new Date(), "Kolonie Knellera", "czarny",
				"Etgar Keret", new BigDecimal(23.50)));
	}

	@Override
	public List<Book> getBooks() {
		return books;
	}
}
