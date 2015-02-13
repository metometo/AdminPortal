package com.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity(name="role")
public class Role implements GrantedAuthority 
{
    @Id
    @GeneratedValue
    private int id;	// user id
    
    String userRole;	// user role

//    @OneToMany
//    private final Collection<Operation> allowedOperations = new ArrayList<Operation>();

    @Override
    public String getAuthority() {
        return userRole;
    }

//    public Collection<Operation> getAllowedOperations() {
//        return allowedOperations;
//    }
}