package ru.kata.spring.boot_security.demo.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "username",unique = true, nullable = false)
   private String username;

   @Column(name = "password")
   private String password;

   @Column(name = "name")
   private String name;

   @Column(name = "surname")
   private String surname;

   @Column(name = "age")
   private int age;

   @ManyToMany(fetch = FetchType.LAZY)
   @Column(name = "roles")
   private Set<Role> roles = new HashSet<>();

   public User() {}

   public User(String username, String name, String surname, int age, Set<Role> roles) {
      this.username = username;
      this.roles = roles;
      this.name = name;
      this.surname = surname;
      this.age = age;
   }

   public User(String username, String password, Set<Role> roles) {
      this.username = username;
      this.password = password;
      this.roles = roles;
   }

   public Long getId() {
      return id;
   }
   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {return name;}
   public void setName(String name) {this.name = name;}

   public String getSurname() {return surname;}
   public void setSurname(String surname) {this.surname = surname;}

   public int getAge() { return age;}
   public void setAge(int age) { this.age = age;}

   public String getUsername() {return username;}
   public void setUsername(String username) {this.username = username;}

   public Set<Role> getRoles() {return roles;}
   public void setRoles(Set<Role> roles) {this.roles = roles;}

   public String getPassword() {return password;}
   public void setPassword(String password) {this.password = password;}

   @Override
   public String toString() {
      return "User: " + username;
   }


}
