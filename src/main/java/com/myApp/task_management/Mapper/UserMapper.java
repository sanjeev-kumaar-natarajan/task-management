package com.myApp.task_management.Mapper;

import com.myApp.task_management.Entity.User;
import com.myApp.task_management.Entity.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toUserDTO(User user) {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUserName(user.getName());
            userDTO.setPassword(user.getPassword());
            userDTO.setEmailId(user.getEmail_id());
            userDTO.setRoleId(user.getRole_id());
            return userDTO;
        } catch (NullPointerException e) {
            throw new NullPointerException("Some or whole userDTO is Null");
        }
    }

    public User toUser(UserDTO userDTO) {
        User user = null;
        try {
            user = new User();
            user.setId(userDTO.getId());
            user.setName(userDTO.getUserName());
            user.setPassword(userDTO.getPassword());
            user.setEmail_id(userDTO.getEmailId());
            user.setRole_id(userDTO.getRoleId());
        } catch (NullPointerException e) {
            throw new NullPointerException("Some or whole userDTO is Null");
        }
        return user;
    }
}
