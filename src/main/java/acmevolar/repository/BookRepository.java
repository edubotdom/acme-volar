
package acmevolar.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

import acmevolar.model.Book;
import acmevolar.model.BookStatusType;
import acmevolar.model.Client;

public interface BookRepository {

	Book findById(int id) throws DataAccessException;

	Collection<Book> findAll() throws DataAccessException;

	void deleteById(int id);

	void save(Book book) throws DataAccessException;

	BookStatusType findBookStatusTypeById(int bookStatusTypeId);

	Client findClientByUsername(@Param("client") String client) throws DataAccessException;

}
