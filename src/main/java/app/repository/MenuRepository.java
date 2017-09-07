package app.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer>{
	
	Menu findByName(@Param("name") String name);
}
