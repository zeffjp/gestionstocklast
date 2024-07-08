package com.gestionstock3.gestionstock.Controller;


import com.gestionstock3.gestionstock.DTO.LoginDTO;
import com.gestionstock3.gestionstock.DTO.UserDTO;
import com.gestionstock3.gestionstock.Message.LoginResponse;
import com.gestionstock3.gestionstock.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String  registerUser(@RequestBody UserDTO userDTO) {
        String id= authService.addUser(userDTO);
        return id;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {

        LoginResponse loginResponse =authService.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }
}
