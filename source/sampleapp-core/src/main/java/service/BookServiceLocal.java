package service;

import java.util.List;

import javax.ejb.Local;

import model.dto.Book;

@Local
public interface BookServiceLocal {
	List<Book> getBooks();
}
