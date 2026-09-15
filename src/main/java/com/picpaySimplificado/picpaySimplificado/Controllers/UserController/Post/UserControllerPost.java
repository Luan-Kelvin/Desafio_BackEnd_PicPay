package com.picpaySimplificado.picpaySimplificado.Controllers.UserController.Post;

import com.picpaySimplificado.picpaySimplificado.DTOs.UserGetDTO;
import com.picpaySimplificado.picpaySimplificado.DTOs.UserPostDTO;
import com.picpaySimplificado.picpaySimplificado.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserControllerPost {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserGetDTO> createUser(@RequestBody UserPostDTO user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }
}
