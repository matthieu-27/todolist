package fr.fms.todolist.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.fms.todolist.dao.TodoListRepository;
import fr.fms.todolist.dao.UserRepository;
import fr.fms.todolist.entities.TodoList;
import fr.fms.todolist.entities.User;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder,
            TodoListRepository todoListRepository) {
        return args -> {
            // Vérifier si un utilisateur existe déjà
            if (userRepository.count() == 0) {
                // Créer un utilisateur par défaut
                User defaultUser = User.builder()
                        .username("john")
                        .password(passwordEncoder.encode("password123"))
                        .email("john@example.com")
                        .build();
                userRepository.save(defaultUser);

                // Créer une TodoList par défaut pour l'utilisateur
                TodoList defaultTodoList = TodoList.builder()
                        .title("Ma Liste de Tâches")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .user(defaultUser)
                        .build();
                todoListRepository.save(defaultTodoList);

                System.out.println("Utilisateur par défaut créé : " + defaultUser.getUsername());
                System.out.println("TodoList par défaut créée : " + defaultTodoList.getTitle());
            }
        };
    }
}
