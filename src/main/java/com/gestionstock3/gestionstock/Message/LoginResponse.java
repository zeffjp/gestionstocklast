package com.gestionstock3.gestionstock.Message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    String message;
    Boolean statut;

    public LoginResponse() {
    }

    public LoginResponse(String message, Boolean statut) {
        this.message = message;
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "message='" + message + '\'' +
                ", statut=" + statut +
                '}';
    }
}
