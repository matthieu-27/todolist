package fr.fms.todolist.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import fr.fms.todolist.dao.CategoryRepository;
import fr.fms.todolist.dao.TaskRepository;
import fr.fms.todolist.entities.Category;
import fr.fms.todolist.entities.Task;
import fr.fms.todolist.enums.Status;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/dashboard")
    public String getProducts() {
        return "tasks";
    }

    @GetMapping("/admin")
    public String adminOnly() {
        return "index";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
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
    public String submitTask(Model model, @Valid Task task, BindingResult result) {
        if (result.hasFieldErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            return "tasks/create";
        }
        taskRepository.save(task);
        return "redirect:/";
    }

    @GetMapping("/create-task")
    public String createTask(Model model) {
        List<Category> categories = categoryRepository.findAll();
        Task task = new Task();
        model.addAttribute("task", task);
        task.getStatus();
        model.addAttribute("status", Status.values());
        model.addAttribute("categories", categories);
        return "tasks/create";
    }
}
