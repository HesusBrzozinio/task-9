package service.ws;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import model.dto.Book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.BookServiceLocal;

@Stateless
@WebService
public class BookServiceWS implements BookServiceRemote {

	private static final Logger LOG = LoggerFactory
			.getLogger(BookServiceWS.class);
	
	@EJB
	private BookServiceLocal bookService;

	@WebMethod(operationName = "getBooks")
	@Override
	public List<Book> getBooks() {
		return bookService.getBooks();
	}

	@WebMethod(operationName = "addBooks")
	@Override
	public int addBook(Book book) {
		LOG.info("book to add: {}", book);
		return 0;
	}

}
