package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UserDetailsImp;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceDetailis;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestAdminController {

    private final UserServiceDetailis userServiceDetailis;
    private final RoleService roleService;
//    private final UserConverter userConverter;

    @Autowired
    public RestAdminController(UserServiceDetailis userServiceDetailis, RoleService roleService) {
        this.userServiceDetailis = userServiceDetailis;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userServiceDetailis.allUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User user = userServiceDetailis.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userServiceDetailis.register(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        userServiceDetailis.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userServiceDetailis.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrent(Principal principal) {
        UserDetailsImp userDetails = (UserDetailsImp) userServiceDetailis.loadUserByUsername(principal.getName());
        User user = userDetails.getUser();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
