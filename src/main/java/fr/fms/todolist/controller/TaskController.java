package fr.fms.todolist.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class TaskController {
    @GetMapping("/products")
    public List<String> getProducts() {
        return List.of("Laptop", "Mobile", "Tablet");
    }

    @GetMapping("/admin")
    public String adminOnly() {
        return "Welcome Admin! You have full access.";
    }

    @GetMapping("/")
    public RedirectView root() {
        return new RedirectView("/index");
    }

    @GetMapping("/index")
    public String index() {
        return "Welcome to index.";
    }
}
