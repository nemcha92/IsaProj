package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Table;

public interface TableRepository extends JpaRepository<Table, Integer> {

}
