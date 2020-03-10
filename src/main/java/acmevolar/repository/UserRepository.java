package acmevolar.repository;

import org.springframework.data.repository.CrudRepository;

import acmevolar.model.User;


public interface UserRepository extends  CrudRepository<User, String>{
	
}
