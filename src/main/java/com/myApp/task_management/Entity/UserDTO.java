package com.myApp.task_management.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    Integer id;
    String userName;
    String password;
    String emailId;
    Integer roleId;

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", emailId='" + emailId + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
