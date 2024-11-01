package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceDetailisImp userServiceDetailisImp;
    private final UserServiceImp userServiceImp;
    private final RoleServiceImp roleServiceImp;


    @Autowired
    public AdminController(UserServiceDetailisImp userServiceDetailisImp,
                           UserServiceImp userServiceImp, RoleServiceImp roleServiceImp) {
        this.userServiceDetailisImp = userServiceDetailisImp;
        this.userServiceImp = userServiceImp;
        this.roleServiceImp = roleServiceImp;

    }



    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userServiceDetailisImp.allUsers());
        return "admin";
    }


    @GetMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        List<Role> roles = roleServiceImp.findAll();
        model.addAttribute("roles", roles);
        return "admin/add";
    }

    @PostMapping("/addUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userServiceImp.register(user);
        return "redirect:/admin";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userServiceDetailisImp.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/editUser")
    public String editUser(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userServiceDetailisImp.findUserById(id));
        List<Role> roles = roleServiceImp.findAll();
        model.addAttribute("roles", roles);
        return "admin/edit-user";
    }


    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userServiceDetailisImp.updateUser(user);
        return "redirect:/admin";
    }
}


