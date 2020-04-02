
package acmevolar.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import acmevolar.model.Book;
import acmevolar.model.BookStatusType;
import acmevolar.model.Client;
import acmevolar.repository.BookRepository;

public interface SpringDataBookRepository extends BookRepository, Repository<Book, Integer> {

	@Override
	@Query("SELECT bookStatusType FROM BookStatusType bookStatusType WHERE bookStatusType.id =:bookStatusTypeId")
	BookStatusType findBookStatusTypeById(int bookStatusTypeId);

	@Override
	@Query("SELECT client FROM Client client WHERE client.user.username =:client ")
	Client findClientByUsername(@Param("client") String client) throws DataAccessException;

	@Override
	@Query("SELECT bookStatusTypes FROM BookStatusType bookStatusTypes")
	List<BookStatusType> findBookStatusTypes();

	@Override
	@Query("SELECT book FROM Book book WHERE book.flight.airline.user.username =:username")
	List<Book> findAirlineBook(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("SELECT book FROM Book book WHERE book.client.user.username =:username")
	List<Book> findClientBook(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("SELECT sum(book.quantity) FROM Book book WHERE book.flight.id = :flightId")
	Integer sumSeatsBooked(@Param("flightId") Integer flightId) throws DataAccessException;

	@Override
	@Query("SELECT book FROM Book book WHERE book.flight.airline.user.username =:username AND book.flight.departDate >= current_date()")
	List<Book> findAirlineBookFuture(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("SELECT book FROM Book book WHERE book.client.user.username =:username AND book.flight.departDate >= current_date()")
	List<Book> findClientBookFuture(@Param("username") String username) throws DataAccessException;

}
