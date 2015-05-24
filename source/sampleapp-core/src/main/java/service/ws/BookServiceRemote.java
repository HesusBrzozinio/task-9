package service.ws;

import java.util.List;

import javax.ejb.Remote;

import model.dto.Book;

@Remote
public interface BookServiceRemote {

	List<Book> getBooks();

	int addBook(final Book book);
}
