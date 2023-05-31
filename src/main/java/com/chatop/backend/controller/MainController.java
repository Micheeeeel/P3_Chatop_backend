package com.chatop.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.chatop.backend.model.DAOUser;
import com.chatop.backend.service.UserService;

@RestController // This means that this class is a RestController bean, and it returns a JSON format into the HTTP request body
@RequestMapping(path="/api") // This means URL's start with /demo (after Application path)
public class MainController {

    @Autowired
    private UserService userService;

    /**
     * Create - Add a new employee
     * @param name of user
     * @param email of user
     * @param password of user
     * @return The user object saved
     */

/*    @PostMapping(path="/auth/register") // Map ONLY POST Requests
    public DAOUser createUser(@RequestParam String name
            , @RequestParam String email, @RequestParam String password) {
        DAOUser n = new DAOUser();
        n.setName(name);
        n.setEmail(email);
        n.setPassword(password);
        return userService.saveUser(n);
    }*/

    /**
     * Read - Get all users
     * @return - An Iterable object of user full filled
     */
    @GetMapping(path="/auth/all")
    public @ResponseBody Iterable<DAOUser> getAllUsers() {
        // This returns a JSON or XML with the users
        return userService.getUsers();
    }

    @GetMapping({ "/api/auth/me" })
    public String getMyDetails() {
        return "Hello me";
    }

/*    *//**
     * Read - Get one employee
     * @param id The id of the user
     * @return An User object full filled
     *//*
    @GetMapping("/employee/{id}")
    public User getEmployee(@PathVariable("id") final Integer id) {
        Optional<User> employee = userService.getUser(id);
        if(employee.isPresent()) {
            return employee.get();
        } else {
            return null;
        }
    }*/

}
