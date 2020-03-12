package acmevolar.repository.springdatajpa;

import org.aspectj.apache.bcel.util.Repository;

import acmevolar.model.Plane;
import acmevolar.repository.PlaneRepository;

public interface SpringDataPlaneRepository extends PlaneRepository, Repository<Plane,Integer> {

}
