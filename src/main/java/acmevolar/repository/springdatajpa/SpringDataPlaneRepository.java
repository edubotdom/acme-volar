
package acmevolar.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import acmevolar.model.Plane;
import acmevolar.repository.PlaneRepository;

public interface SpringDataPlaneRepository extends PlaneRepository, Repository<Plane, Integer> {
	
}
