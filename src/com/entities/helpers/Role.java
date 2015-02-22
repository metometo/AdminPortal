package com.entities.helpers;

import java.io.Serializable;
import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority, Serializable
{
	RoleType userRole;	// user role

    @Override
    public String getAuthority() 	// returns the role as string
    {
    	return userRole.toString();
    }   
    
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