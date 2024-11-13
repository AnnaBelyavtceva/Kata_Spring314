package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.security.UserDetailsImp;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceDetailisImp implements UserServiceDetailis {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceDetailisImp(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public void register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User updateUser) {
        User user = userRepository.findById(updateUser.getId()).orElseThrow(()
                -> new IllegalArgumentException("User not found"));
        if (updateUser.getPassword() != null && !updateUser.getPassword().isBlank()
                && !passwordEncoder.matches(updateUser.getPassword(), user.getPassword())) {

            updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }  else {

            updateUser.setPassword(user.getPassword());
        }
        userRepository.save(updateUser);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }


}


