package com.wanxian.jpa.controller;

import com.wanxian.jpa.entity.User;
import com.wanxian.jpa.repository.UserRepository;
import com.wanxian.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("save")
    public User save(@RequestBody User user) {
        userService.add(user);
        return userService.getOne(user.getId());
    }

    @RequestMapping("all")
    public List<User> all() {
        return userRepository.findAll();
    }


    @RequestMapping("one")
    public User one(Long id) {
        return userService.getOne(id);
    }


}
