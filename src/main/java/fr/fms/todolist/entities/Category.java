package fr.fms.todolist.entities;

import java.io.Serializable;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Category implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank(message = "Name must not be blank.")
  private String name;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private Collection<Task> tasks;

  public Category(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
  // #region Getter & Setter

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String value) {
    this.name = value;
  }

  // #endregion
}
