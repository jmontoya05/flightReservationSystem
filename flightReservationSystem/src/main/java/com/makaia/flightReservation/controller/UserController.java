package com.makaia.flightReservation.controller;

import com.makaia.flightReservation.dto.UserDTO;
import com.makaia.flightReservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("{userId}/{roleId}")
    public ResponseEntity<UserDTO> setRole(@PathVariable(name = "userId") Integer userId, @PathVariable(name = "roleId") Integer roleId ){
        return new ResponseEntity<>(userService.setRole(userId, roleId), HttpStatus.OK);
    }
}
