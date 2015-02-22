package com.entities.helpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;


public class Role implements GrantedAuthority, Serializable
{
   
    //private int id;	// user id
    
	RoleType userRole;	// user role

//    @OneToMany
//    private final Collection<Operation> allowedOperations = new ArrayList<Operation>();

    @Override
    public String getAuthority() {
    	return userRole.toString();
    }

//    public Collection<Operation> getAllowedOperations() {
//        return allowedOperations;
//    }
    
    
    @Override
    public String toString()
    {
    	return userRole.toString();
    }

	public RoleType getUserRole()
	{
		return userRole;
	}

	public void setUserRole(RoleType userRole)
	{
		this.userRole = userRole;
	}
	
	
	
}