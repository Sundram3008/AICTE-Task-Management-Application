package com.sundram.aictetaskmanagement.model;

import org.springframework.security.core.GrantedAuthority;

public class Authorities implements GrantedAuthority {

    private String authority;

    @Override
    public String toString() {
        return "Authorities [authority=" + authority + "]";
    }

    public Authorities(String authority) {
        this.authority = authority;
    }

    public Authorities() {
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
