package app.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Serializable> {

}
