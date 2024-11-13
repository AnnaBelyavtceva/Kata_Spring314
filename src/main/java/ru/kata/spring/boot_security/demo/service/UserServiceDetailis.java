package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;


public interface UserServiceDetailis extends UserDetailsService {

    public void register(User user);

    public User findUserById(Long id);

    public List<User> allUsers();

    public void delete(Long id);

    public void updateUser(User user);

    public User findByUsername(String username);



}