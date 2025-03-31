package com.myApp.task_management.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users", schema = "authentication")
public class User {

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user_name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email_id='" + email_id + '\'' +
                ", role_id=" + role_id +
                '}';
    }

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    String name;

    @NotNull
    String password;

    @NotNull
    String email_id;

    Integer role_id;
}
