package pl.kb.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.dto.Book;
import service.BookServiceLocal;

@ManagedBean(name = "booksView")
@SessionScoped
public class BooksViewController implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Book> books;

	@EJB
	private BookServiceLocal bookService;

	@PostConstruct
	private void init() {
		books = bookService.getBooks();
	}

	public List<Book> getBooks() {
		return books;
	}
}
