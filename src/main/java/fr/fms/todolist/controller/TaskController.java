package fr.fms.todolist.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.security.core.Authentication;

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
        return "redirect:/home";
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
    public String index(Model model, Authentication authentication) {
        List<Task> todo, doing, done;
        List<Category> categories = categoryRepository.findAll();

        if (authentication != null && authentication.isAuthenticated()) {
            // Utilisateur authentifié : afficher les tâches réelles
            todo = taskRepository.findByStatus(Status.TODO);
            doing = taskRepository.findByStatus(Status.DOING);
            done = taskRepository.findByStatus(Status.DONE);
        } else {
            // Utilisateur non authentifié : afficher des tâches fictives
            todo = createFakeTasks(Status.TODO);
            doing = createFakeTasks(Status.DOING);
            done = createFakeTasks(Status.DONE);
        }

        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("status", Status.values());
        model.addAttribute("categories", categories);
        model.addAttribute("todo", todo);
        model.addAttribute("doing", doing);
        model.addAttribute("done", done);
        return "tasks";
    }

    private List<Task> createFakeTasks(Status status) {
        List<Task> tasks = new ArrayList<>();
        Category fakeCategory = new Category("Exemple");

        for (int i = 1; i <= 3; i++) {
            Task task = Task.builder()
                    .title("Tâche fictive " + i + " - " + status)
                    .description("Description de la tâche fictive " + i)
                    .status(status)
                    .category(fakeCategory)
                    .build();
            tasks.add(task);
        }

        return tasks;
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        return "redirect:/home";
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
        model.addAttribute("status", Status.values());
        model.addAttribute("categories", categories);
        return "tasks/create";
    }

    @GetMapping("/create-category")
    public String createCategory(Model model) {
        return "categories/create";
    }

    @GetMapping("/delete-task/{taskId}")
    public String deleteTask(@PathVariable long taskId) {
        taskRepository.deleteById(taskId);
        return "redirect:/";
    }

    @GetMapping("/edit-task/{taskId}")
    public String editTask(@PathVariable long taskId, Model model) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            model.addAttribute("task", task);
            return "tasks";
        }
        return "redirect:/";
    }

    @GetMapping("/delete-category/{categoryId}")
    public String deleteCategory(@PathVariable long categoryId) {
        categoryRepository.deleteById(categoryId);
        return "redirect:/";
    }

}
