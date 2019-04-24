package com.wanxian.controller;

import com.wanxian.entity.User;
import com.wanxian.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("save")
    public User save(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @GetMapping("getUser/{id}")
    public User getUser(@PathVariable String id) {
        User user = userRepository.getUser(id);
        return user;
    }


}
