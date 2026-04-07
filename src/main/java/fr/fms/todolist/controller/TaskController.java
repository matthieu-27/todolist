package fr.fms.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import fr.fms.todolist.entities.Task;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class TaskController {

    @GetMapping("/dashboard")
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

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        return "error";
    }

    @PostMapping("/create-task")
    public String submitTask(@RequestParam("param") String param) {
        return "tasks/create";
    }

    @GetMapping("/create-task")
    public String createTask(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        return "tasks/create";
    }
}
