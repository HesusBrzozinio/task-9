package service;

import java.util.List;

import javax.ejb.Remote;

import model.dto.Book;

@Remote
public interface BookServiceRemote {
	List<Book> getBooks();
}
