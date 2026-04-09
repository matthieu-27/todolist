package fr.fms.todolist.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "redirect:/home";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String index(Model model, Authentication authentication,
            @RequestParam(required = false) Boolean weeklyView) {
        List<Task> todo, doing, done;
        List<Category> categories = categoryRepository.findAll();

        if (authentication != null && authentication.isAuthenticated()) {
            // Utilisateur authentifié : afficher les tâches réelles
            if (weeklyView != null && weeklyView) {
                // Affichage par semaine
                Map<String, List<Task>> tasksByWeek = groupTasksByWeek(taskRepository.findAll());
                model.addAttribute("tasksByWeek", tasksByWeek);
                model.addAttribute("weeklyView", true);
            } else {
                // Affichage normal
                todo = taskRepository.findByStatus(Status.TODO);
                doing = taskRepository.findByStatus(Status.DOING);
                done = taskRepository.findByStatus(Status.DONE);
                model.addAttribute("todo", todo);
                model.addAttribute("doing", doing);
                model.addAttribute("done", done);
            }
        } else {
            // Utilisateur non authentifié : afficher des tâches fictives
            if (weeklyView != null && weeklyView) {
                // Affichage par semaine
                Map<String, List<Task>> fakeTasksByWeek = groupFakeTasksByWeek();
                model.addAttribute("tasksByWeek", fakeTasksByWeek);
                model.addAttribute("weeklyView", true);
            } else {
                // Affichage normal
                todo = createFakeTasks(Status.TODO);
                doing = createFakeTasks(Status.DOING);
                done = createFakeTasks(Status.DONE);
                model.addAttribute("todo", todo);
                model.addAttribute("doing", doing);
                model.addAttribute("done", done);
            }
        }

        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("status", Status.values());
        model.addAttribute("categories", categories);
        return "tasks";
    }

    private Map<String, List<Task>> groupTasksByWeek(List<Task> tasks) {
        return tasks.stream()
                .filter(task -> task.getScheduledAt() != null)
                .collect(Collectors.groupingBy(task -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(task.getScheduledAt());
                    int week = cal.get(Calendar.WEEK_OF_YEAR);
                    int year = cal.get(Calendar.YEAR);
                    return "Semaine " + week + " - " + year;
                }));
    }

    private Map<String, List<Task>> groupFakeTasksByWeek() {
        List<Task> fakeTasks = new ArrayList<>();
        for (Status status : Status.values()) {
            fakeTasks.addAll(createFakeTasks(status));
        }
        return groupTasksByWeek(fakeTasks);
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
            model.addAttribute("status", Status.values());
            model.addAttribute("categories", categoryRepository.findAll());
            return "tasks/form";
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
        return "tasks/form";
    }

    @GetMapping("/create-category")
    public String createCategory(Model model) {
        return "categories/create";
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + taskId));
        return ResponseEntity.ok(task);
    }

    @PostMapping("/update-task/{taskId}")
    public String updateTask(@PathVariable long taskId, @Valid Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("status", Status.values());
            model.addAttribute("categories", categoryRepository.findAll());
            return "tasks/form";
        }
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + taskId));
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setScheduledAt(task.getScheduledAt());
        existingTask.setCategory(task.getCategory());
        taskRepository.save(existingTask);
        return "redirect:/";
    }

    @GetMapping("/delete-task/{taskId}")
    public String deleteTask(@PathVariable long taskId) {
        taskRepository.deleteById(taskId);
        return "redirect:/";
    }

    @GetMapping("/edit-task/{taskId}")
    public String editTask(@PathVariable long taskId, Model model) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + taskId));
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("task", task);
        model.addAttribute("status", Status.values());
        model.addAttribute("categories", categories);
        return "tasks/form";
    }

    @GetMapping("/delete-category/{categoryId}")
    public String deleteCategory(@PathVariable long categoryId) {
        categoryRepository.deleteById(categoryId);
        return "redirect:/";
    }

}
