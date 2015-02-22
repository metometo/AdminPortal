package com.entities;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.entities.helpers.BaseEntity;
import com.entities.helpers.RoleType;

@Entity
@Table(name="user", uniqueConstraints = @UniqueConstraint(columnNames = {"userName"}))
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "user_id")),
	@AttributeOverride(name = "name", column = @Column(name = "first_name"))
})
public class User extends BaseEntity implements Serializable
{
	@Column(name="userName")	// column name is needed for the spring security 
	private String userName;
	
	@Column(name="password")
	private String password;
    
	@Column(name="last_name")
    private String lastName;
    
	@Column(name="role")
    RoleType role;


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


	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
}