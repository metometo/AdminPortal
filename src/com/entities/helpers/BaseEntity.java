package com.entities.helpers;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity
{
	@Id
	@GeneratedValue
	protected int id;
	protected String name;
	
	
	
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
	
	public void setName(String appName)
	{
		this.name = appName;
	}
}
