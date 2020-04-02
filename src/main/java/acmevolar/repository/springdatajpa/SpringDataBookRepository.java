
package acmevolar.repository.springdatajpa;

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

}
