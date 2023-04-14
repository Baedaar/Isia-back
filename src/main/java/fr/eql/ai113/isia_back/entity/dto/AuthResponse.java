package fr.eql.ai113.isia_back.entity.dto;

import org.springframework.security.core.userdetails.UserDetails;

public class AuthResponse {

    private UserDetails compte;
    private String token;

    public AuthResponse(UserDetails compte, String token) {
        this.compte = compte;
        this.token = token;
    }

    /// Getters ///
    public UserDetails getCompte() {
        return compte;
    }
    public String getToken() {
        return token;
    }
}
