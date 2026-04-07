package fr.fms.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class TaskController {

    @GetMapping("/products")
    public String getProducts() {
        return "tasks";
    }

    @GetMapping("/admin")
    public String adminOnly() {
        return "index";
    }

    @GetMapping("/")
    public RedirectView root() {
        return new RedirectView("/home");
    }

    @GetMapping("/home")
    public String index() {
        return "tasks";
    }
}
