package com.sundram.aictetaskmanagement.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity(name = "Users")
public class Users implements UserDetails{

    
    private String name;
    private String username;
    
    @Column(unique=true)
    private String emailId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;


    private String role;

    private String contactNumber;

    private String address_line1;
    
    @Nullable
    private String address_line2;

    @Nullable
    private String password;

    private Date dob;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authorities> set = new HashSet<>();
        set.add(new Authorities(this.role)); 
        return set;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
