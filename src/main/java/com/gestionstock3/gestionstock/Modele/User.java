package com.gestionstock3.gestionstock.Modele;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id",length = 45)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(name = "user_name",length = 255)
    private String username;

    @Column(name="email",length = 255)
    private String email;

    @Column(name = "password")
    private String password;

    // Getters and setters, constructors, etc.

    public User() {
    }

    public User(Long userid, String username, String email, String password) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    // Getters and setters
}
