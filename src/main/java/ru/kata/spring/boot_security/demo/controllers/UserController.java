package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    private final UserService us;

    @Autowired
    public UserController(UserService us) {
        this.us = us;
    }

    @GetMapping(value = "/")
    public String printWelcome() {
        return "index";
    }

    @GetMapping(value = "/user")
    public String userPage(Model model, Principal principal) {
        User user = us.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/")
    public String createUser(@ModelAttribute("user") @Valid User user, @RequestParam("rolesForUser") Long[] rolesForUser
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin";
        }
        Set<Role> roles= us.getRolesByArrayIds(rolesForUser);
        us.addUser(user, roles);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam long id) {
        us.removeUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") @Valid User userUpd, @RequestParam("rolesesForUser") Long[] rolesesForUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin";
        }
        Set<Role> roles= us.getRolesByArrayIds(rolesesForUser);
        us.updateUser(userUpd, roles);
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String test(Model model, Principal principal) {
        List<User> users = us.userList();
        model.addAttribute("users", users);
        User userOne = us.findByUsername(principal.getName());
        model.addAttribute("userOne", userOne);
        model.addAttribute("userNew", new User());
        model.addAttribute("allRoles", us.getAllRoles());
        return "admin";
    }

}
