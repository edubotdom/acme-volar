package acmevolar.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

import acmevolar.model.User;


public interface UserRepository extends  CrudRepository<User, String>{
	
	Collection<User> findAll() throws DataAccessException;
}
