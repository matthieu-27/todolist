package fr.fms.todolist.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.fms.todolist.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
