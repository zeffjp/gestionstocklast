package com.gestionstock3.gestionstock.Service.Impl;

import com.gestionstock3.gestionstock.DTO.LoginDTO;
import com.gestionstock3.gestionstock.DTO.UserDTO;
import com.gestionstock3.gestionstock.Message.LoginResponse;
import com.gestionstock3.gestionstock.Modele.User;
import com.gestionstock3.gestionstock.Repository.UserRepository;
import com.gestionstock3.gestionstock.Service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public String addUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getUserid(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                this.passwordEncoder.encode(userDTO.getPassword())
        );
        userRepository.save(user);
        return user.getUsername();
    }

    @Override
    public LoginResponse loginUser(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail());
        if (user != null) {
            String rawPassword = loginDTO.getPassword();
            String encodedPassword = user.getPassword();
            boolean isPasswordCorrect = passwordEncoder.matches(rawPassword, encodedPassword);
            if (isPasswordCorrect) {
                return new LoginResponse("Connexion réussie", true);
            } else {
                return new LoginResponse("Mot de passe incorrect", false);
            }
        } else {
            return new LoginResponse("Email incorrect", false);
        }
    }
}
