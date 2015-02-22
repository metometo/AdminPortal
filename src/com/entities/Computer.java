package com.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.entities.helpers.BaseEntity;

@Entity
@Table(name="computer")
public class Computer extends BaseEntity implements Serializable
{
//	@Id
//	@GeneratedValue
//	private int id;
//	
//	private String name;
	private String ip;
	private String login;
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


//	@Override
//	public String toString()
//	{
//		// TODO Auto-generated method stub
//		return "comp.name: "+name + ", ip: " + ip + "";
//	}
	
}
