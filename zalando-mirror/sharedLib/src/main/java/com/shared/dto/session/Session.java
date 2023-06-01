package com.shared.dto.session;


public class Session {
    private String token;
    private Role role;
    private long userID;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public long getUserID() {
        return userID;
    }
    public void setUserID(long userID) {
        this.userID = userID;
    }
}
