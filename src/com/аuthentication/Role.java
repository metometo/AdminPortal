package com.àuthentication;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority
{

	@Override
	public String getAuthority()
	{
		
		return "ROLE_USER";
	}
	
}