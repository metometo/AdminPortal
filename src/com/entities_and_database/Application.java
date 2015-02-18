package com.entities_and_database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Application
{
	@Id
	@GeneratedValue
	private int id;
	
	private String appName;
	private String vendorName;
	private String licanseRequired;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getAppName()
	{
		return appName;
	}
	public void setAppName(String appName)
	{
		this.appName = appName;
	}
	public String getVendorName()
	{
		return vendorName;
	}
	public void setVendorName(String vendorName)
	{
		this.vendorName = vendorName;
	}
	public String getLicanseRequired()
	{
		return licanseRequired;
	}
	public void setLicanseRequired(String licanseRequired)
	{
		this.licanseRequired = licanseRequired;
	}
	
}
