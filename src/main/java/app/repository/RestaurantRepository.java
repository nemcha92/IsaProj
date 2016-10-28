package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.Restaurant;



@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

}
