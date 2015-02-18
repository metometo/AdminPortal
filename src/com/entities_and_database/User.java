package com.entities_and_database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchProfile.FetchOverride;

@Entity(name="user")
@Table(uniqueConstraints =  @UniqueConstraint(columnNames = {"userName"}))
public class User implements Serializable
{
    @Id
    @GeneratedValue
    private int id;
    
  
    private String userName;
    private String password;
    
    private String firstName;
    private String lastName;

    //@ManyToMany(cascade=CascadeType.ALL)
    //private List<Role> roles = new ArrayList<Role>();
    String role;

    
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

//	public List<Role> getRoles()
//	{
//		return roles;
//	}
//	
//	public void setRoles(List<Role> roles)
//	{
//		this.roles = roles;
//	}

	public String getPassword()
	{
		return password;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "id: " + id +", user: " + userName + ", password: " + password + 
				", first name: " + firstName + ", last name: " + lastName + ", role: " + role;
	}
}