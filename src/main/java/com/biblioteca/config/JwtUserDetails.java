package com.biblioteca.config;

public class JwtUserDetails {

    private final Integer userId;
    private final String username;
    private final Integer rol;

    public JwtUserDetails(Integer userId, String username, Integer rol) {
        this.userId = userId;
        this.username = username;
        this.rol = rol;
    }

    public Integer getUserId() { return userId; }
    public String getUsername() { return username; }
    public Integer getRol() { return rol; }
}
