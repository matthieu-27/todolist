package fr.fms.todolist.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.fms.todolist.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
