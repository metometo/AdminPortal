package com.entities_and_database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Computer implements Serializable
{
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	private String ip;
	private String login;
	private String password;
	
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//@JoinColumn(name="id")
	//Collection<Application> applications = new ArrayList<Application>();
	
//	public Collection<Application> getApplications()
//	{
//		return applications;
//	}
//	public void setApplications(Collection<Application> applications)
//	{
//		this.applications = applications;
//	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
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


	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "comp.name: "+name + ", ip: " + ip + "";
	}
	
}
