package com.gestionstock3.gestionstock.Service;


import com.gestionstock3.gestionstock.DTO.LoginDTO;
import com.gestionstock3.gestionstock.DTO.UserDTO;
import com.gestionstock3.gestionstock.Message.LoginResponse;

public interface AuthService {
       String addUser(UserDTO userDTO);
       LoginResponse loginUser(LoginDTO loginDTO);
}
