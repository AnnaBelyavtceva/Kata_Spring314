package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.*;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceDetailis userServiceDetailis;
    private final RoleServiceImp roleServiceImp;


    @Autowired
    public AdminController(UserServiceDetailis userServiceDetailis, RoleServiceImp roleServiceImp) {
        this.userServiceDetailis = userServiceDetailis;
        this.roleServiceImp = roleServiceImp;

    }


    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userServiceDetailis.allUsers());
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
        userServiceDetailis.register(user);
        return "redirect:/admin";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userServiceDetailis.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/editUser")
    public String editUser(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userServiceDetailis.findUserById(id));
        List<Role> roles = roleServiceImp.findAll();
        model.addAttribute("roles", roles);
        return "admin/edit-user";
    }


    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userServiceDetailis.register(user);
        return "redirect:/admin";
    }
}


