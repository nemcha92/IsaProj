package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUsername(@Param("username") String username); 
}
