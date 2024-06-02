package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String adminPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("authUser", auth.getPrincipal());
        model.addAttribute("currentRole", "ADMIN");
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        model.addAttribute("editUser", new User());
        return "mainPartPage";
    }


    @PostMapping("/addUser")
    public String addUser(User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/deleteUser/{id}")
    public String showDeleteConfirmation(@PathVariable("id") int id, Model model) {
        User userToDelete = userService.findUserById(id);
        model.addAttribute("userToDelete", userToDelete);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("authUser", auth.getPrincipal());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        model.addAttribute("currentRole", "ADMIN");
        return "mainPartPage";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/editUser/{id}")
    public String showEditUser(@PathVariable("id") int id, Model model) {
        User user = userService.findUserById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userToEdit", user);
        model.addAttribute("authUser", auth.getPrincipal());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("currentRole", "ADMIN");
        return "mainPartPage";
    }

    @PostMapping("/updateUser")
    public String updateUser(User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/profile")
    public String userProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal(); // Получаем email текущего пользователя
        model.addAttribute("authUser", user);
        model.addAttribute("currentRole", "Profile");
        model.addAttribute("user", user);
        return "mainPartPage";
    }

}
