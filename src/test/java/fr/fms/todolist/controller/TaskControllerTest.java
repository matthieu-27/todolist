package fr.fms.todolist.controller;

import fr.fms.todolist.config.SecurityConfig;
import fr.fms.todolist.dao.CategoryRepository;
import fr.fms.todolist.dao.TaskRepository;
import fr.fms.todolist.dao.UserRepository;
import fr.fms.todolist.entities.Task;
import fr.fms.todolist.enums.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest loads only the web layer (controller + MockMvc).
// @Import(SecurityConfig.class) is required — @WebMvcTest does NOT scan @Configuration
// beans automatically, so without it Spring falls back to default HTTP Basic auth.
// No real DB needed — repositories are replaced by @MockBean stubs.
@WebMvcTest(TaskController.class)
@Import(SecurityConfig.class)
class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskRepository taskRepository;

    @MockBean
    CategoryRepository categoryRepository;

    // SecurityConfig injects UserRepository — must mock it so the bean loads
    // and our permitAll()/hasRole() rules from SecurityConfig apply correctly.
    @MockBean
    UserRepository userRepository;

    // --- GET /home -----------------------------------------------------------

    @Test
    void home_unauthenticated_renders_fake_tasks() throws Exception {
        when(categoryRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks"))
                .andExpect(model().attribute("isAuthenticated", false));
    }

    @Test
    @WithMockUser(username = "lbertrand", roles = "USER")
    void home_authenticated_returns_tasks_from_repository() throws Exception {
        Task task = Task.builder().title("Corriger l'affichage du tableau de bord").status(Status.TODO).build();
        when(taskRepository.findByStatus(Status.TODO)).thenReturn(List.of(task));
        when(taskRepository.findByStatus(Status.DOING)).thenReturn(List.of());
        when(taskRepository.findByStatus(Status.DONE)).thenReturn(List.of());
        when(categoryRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks"))
                .andExpect(model().attribute("isAuthenticated", true))
                .andExpect(model().attribute("todo", hasSize(1)))
                .andExpect(model().attribute("doing", hasSize(0)))
                .andExpect(model().attribute("done", hasSize(0)));
    }

    // --- Protected route redirects unauthenticated ---------------------------

    @Test
    void createTask_unauthenticated_redirects_to_login() throws Exception {
        mockMvc.perform(get("/create-task"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    // --- POST /create-task ---------------------------------------------------

    @Test
    @WithMockUser(username = "lbertrand", roles = "USER")
    void createTask_valid_persists_task_and_redirects() throws Exception {
        mockMvc.perform(post("/create-task")
                        .param("title", "Préparer la réunion de lundi")
                        .param("status", "TODO")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    @WithMockUser(username = "lbertrand", roles = "USER")
    void createTask_blank_title_shows_form_with_errors() throws Exception {
        when(categoryRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(post("/create-task")
                        .param("title", "")
                        .param("status", "TODO")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks/form"))
                .andExpect(model().attributeExists("errors"));
    }

    // --- GET /home?weeklyView=true -------------------------------------------

    @Test
    void home_weeklyView_unauthenticated_renders_fake_tasks_by_week() throws Exception {
        when(categoryRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/home").param("weeklyView", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks"))
                .andExpect(model().attribute("weeklyView", true))
                // fake tasks now have scheduledAt set → map must not be empty
                .andExpect(model().attributeExists("tasksByWeek"));
    }

    @Test
    @WithMockUser(username = "lbertrand", roles = "USER")
    void home_weeklyView_authenticated_groups_tasks_by_week_key() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.JANUARY, 6); // week 2 of 2025
        Date week2 = cal.getTime();

        Task task = Task.builder().title("Mettre à jour la documentation API").status(Status.TODO).scheduledAt(week2).build();
        when(taskRepository.findAll()).thenReturn(List.of(task));
        when(categoryRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/home").param("weeklyView", "true"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("weeklyView", true))
                .andExpect(model().attribute("tasksByWeek", hasKey("Semaine 2 - 2025")));
    }

    @Test
    @WithMockUser(username = "lbertrand", roles = "USER")
    void home_weeklyView_tasks_without_scheduledAt_excluded() throws Exception {
        Task withDate = Task.builder().title("Appel client Dupont").status(Status.TODO)
                .scheduledAt(new Date()).build();
        Task withoutDate = Task.builder().title("Trier les emails en attente").status(Status.DOING).build();
        when(taskRepository.findAll()).thenReturn(List.of(withDate, withoutDate));
        when(categoryRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/home").param("weeklyView", "true"))
                .andExpect(status().isOk())
                // only 1 week bucket (withoutDate filtered out)
                .andExpect(model().attribute("tasksByWeek", aMapWithSize(1)));
    }

    @Test
    @WithMockUser(username = "lbertrand", roles = "USER")
    void home_weeklyView_two_tasks_same_week_grouped_together() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MARCH, 10); // both in week 11 of 2025
        Date day1 = cal.getTime();
        cal.set(2025, Calendar.MARCH, 12);
        Date day2 = cal.getTime();

        Task t1 = Task.builder().title("Relancer Élodie pour le contrat").status(Status.TODO).scheduledAt(day1).build();
        Task t2 = Task.builder().title("Vérifier les accès staging").status(Status.DONE).scheduledAt(day2).build();
        when(taskRepository.findAll()).thenReturn(List.of(t1, t2));
        when(categoryRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/home").param("weeklyView", "true"))
                .andExpect(status().isOk())
                // 1 bucket with 2 tasks inside
                .andExpect(model().attribute("tasksByWeek", aMapWithSize(1)))
                .andExpect(model().attribute("tasksByWeek", hasKey("Semaine 11 - 2025")));
    }

    // --- GET /task/{id} returns JSON -----------------------------------------

    @Test
    @WithMockUser(username = "lbertrand", roles = "USER")
    void getTask_existing_returns_json() throws Exception {
        Task task = Task.builder().title("Revoir le devis fournisseur").status(Status.DOING).build();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/task/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Revoir le devis fournisseur"))
                .andExpect(jsonPath("$.status").value("DOING"));
    }

    @Test
    @WithMockUser(username = "lbertrand", roles = "USER")
    void getTask_notFound_returns_404() throws Exception {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/task/99"))
                .andExpect(status().isNotFound());
    }
}
