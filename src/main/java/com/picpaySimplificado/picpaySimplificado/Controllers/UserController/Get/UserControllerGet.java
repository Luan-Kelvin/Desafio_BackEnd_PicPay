package com.picpaySimplificado.picpaySimplificado.Controllers.UserController.Get;

import com.picpaySimplificado.picpaySimplificado.DTOs.UserGetDTO;
import com.picpaySimplificado.picpaySimplificado.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserControllerGet {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserGetDTO>> listUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.listUsers());
    }
}
