package com.myApp.task_management.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDTO {

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", completed=" + completed +
                '}';
    }

    public TaskDTO() {
    }

    public TaskDTO(Long id, String title, String description, Priority priority, LocalDate dueDate, Boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    @Id
    @NotNull()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull(message = "Title cannot be null")
    String title;

    String description;

    @Enumerated(EnumType.STRING)
    Priority priority;

    LocalDate dueDate;

    Boolean completed = false;
}
