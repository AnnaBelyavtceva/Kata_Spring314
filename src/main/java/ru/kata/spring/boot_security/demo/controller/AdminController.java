package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UserDetailsImp;
import ru.kata.spring.boot_security.demo.service.*;
import java.util.List;



@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceDetailis userServiceDetailis;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserServiceDetailis userServiceDetailis, RoleService roleService) {
        this.userServiceDetailis = userServiceDetailis;
        this.roleService = roleService;

    }


    @GetMapping()
    public String showAllUsers(Model model,  @AuthenticationPrincipal UserDetailsImp userDetail) {
        model.addAttribute("users", userServiceDetailis.allUsers());
        User user = userDetail.getUser();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("newUser",new User());
        return "admin";
    }
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userServiceDetailis.delete(id);
        return "redirect:/admin";
    }
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userServiceDetailis.updateUser(user);
        return "redirect:/admin";
    }


    @PostMapping("/addUser")
    public String saveUser(@ModelAttribute("newUser") User user) {
        userServiceDetailis.register(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userServiceDetailis.findUserById(id));
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        return "admin";
    }

}


