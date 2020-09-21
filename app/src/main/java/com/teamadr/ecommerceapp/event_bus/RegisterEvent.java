package com.teamadr.ecommerceapp.event_bus;

public class RegisterEvent {
    String username;

    public RegisterEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
