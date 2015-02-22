package com.entities;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.entities.helpers.BaseEntity;

@Entity
@Table(name="computer")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "computer_id")),
	@AttributeOverride(name = "name", column = @Column(name = "computer_name"))
})
public class Computer extends BaseEntity implements Serializable
{
	
	@Column(name="ip_address")
	private String ip;
	
	@Column(name="login")
	private String login;
	
	@Column(name="password")
	private String password;
	
	
	public String getIp()
	{
		return ip;
	}
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	public String getLogin()
	{
		return login;
	}
	public void setLogin(String login)
	{
		this.login = login;
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
