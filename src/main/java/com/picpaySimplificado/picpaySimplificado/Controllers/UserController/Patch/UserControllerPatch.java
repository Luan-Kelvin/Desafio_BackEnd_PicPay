package com.picpaySimplificado.picpaySimplificado.Controllers.UserController.Patch;

import com.picpaySimplificado.picpaySimplificado.DTOs.DepositDTO;
import com.picpaySimplificado.picpaySimplificado.DTOs.UserGetDTO;
import com.picpaySimplificado.picpaySimplificado.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserControllerPatch {

    private final UserService userService;

    @PatchMapping("/deposit")
    public ResponseEntity<UserGetDTO> deposit(@RequestBody DepositDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.deposit(dto));
    }
}
