package com.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="user")
public class User
{
    @Id
    @GeneratedValue
    private int id;
    private String userName;
    private String password;

    @OneToMany
    private List<Role> roles = new ArrayList<Role>();

    
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public List<Role> getRoles()
	{
		return roles;
	}
	
	public void setRoles(List<Role> roles)
	{
		this.roles = roles;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	
	
}