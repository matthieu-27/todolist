package fr.fms.todolist.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.fms.todolist.entities.TodoList;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {

}
