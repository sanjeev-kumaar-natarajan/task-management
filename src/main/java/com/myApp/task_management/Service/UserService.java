package com.myApp.task_management.Service;

import com.myApp.task_management.Entity.User;
import com.myApp.task_management.Entity.UserDTO;
import com.myApp.task_management.Mapper.UserMapper;
import com.myApp.task_management.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userMapper.toUser(userDTO);
        User createdUser = userRepository.save(user);
        log.info("User with id - {} and roleId - {} created !", createdUser.getId(), createdUser.getRole_id());
        UserDTO createdUserDTO = userMapper.toUserDTO(createdUser);
        return createdUserDTO;
    }

    public User findByUsername(String username) {
        return userRepository.findByName(username);
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByName(userName);
        if (user == null)
            throw new UsernameNotFoundException("User not found: " + userName);
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                Collections.emptyList());

    }
}
