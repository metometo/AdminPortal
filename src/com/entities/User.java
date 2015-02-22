package com.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchProfile.FetchOverride;

import com.entities.helpers.BaseEntity;
import com.entities.helpers.RoleType;

@Entity
@Table(name="user", uniqueConstraints = @UniqueConstraint(columnNames = {"userName"}))
public class User extends BaseEntity implements Serializable
{
//    @Id
//    @GeneratedValue
//    private int id;
//  
   private String userName;
    private String password;
    
    //private String name;
    private String lastName;

    //@ManyToMany(cascade=CascadeType.ALL)
    //private List<Role> roles = new ArrayList<Role>();
    
    RoleType role;

   

   
//	public List<Role> getRoles()
//	{
//		return roles;
//	}
//	
//	public void setRoles(List<Role> roles)
//	{
//		this.roles = roles;
//	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public RoleType getRole()
	{
		return role;
	}

	public void setRole(RoleType role)
	{
		this.role = role;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

//	public String getFirstName()
//	{
//		return firstName;
//	}
//
//	public void setFirstName(String firstName)
//	{
//		this.firstName = firstName;
//	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
//	@Override
//	public String toString()
//	{
//		// TODO Auto-generated method stub
//		return "id: " + id +", user: " + userName + ", password: " + password + 
//				", first name: " + firstName + ", last name: " + lastName + ", role: " + role;
//	}
}