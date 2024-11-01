package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.security.UserDetailsImp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceDetailisImp implements UserDetailsService {

    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserServiceDetailisImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isEmpty()) {
        throw new UsernameNotFoundException("User not found");
    }
    return new UserDetailsImp(user.get());
    }

//    @Transactional
//        public void findByUsername(String username) {
//        this.userRepository.findByUsername(username);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(userRepository.findByUsername(username));
//    }



    @Transactional
    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(new User());
    }
    @Transactional
    public List<User> allUsers() {
        return userRepository.findAll();
    }
    @Transactional
    public void delete(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }
    @Transactional
    public void save(User user) {
        Optional<User> userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return;
        }
        user.setRoles(Collections.singleton(new Role( "ROLE_USER",1L)));
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }

}


