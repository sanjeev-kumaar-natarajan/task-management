package com.myApp.task_management.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "task", schema = "task_management")
public class Task {

    public Task(Long id, String title, String description, Priority priority, LocalDate dueDate, Boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public Task() {

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


    @Column(name = "due_date")
    LocalDate dueDate;
    @Column(columnDefinition = "boolean default false")
    Boolean completed = false;

    public Task(String mockTask, String mockDescription, Priority priority, LocalDate localDate, boolean b) {
    }
}
