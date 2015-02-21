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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Application implements Serializable
{
		@Id
	@GeneratedValue
	private int id;
	
	private String appName;
	private String vendorName;
	private boolean licenseRequired;
	
	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER) //mappedBy="applications",
	Collection<Computer> computers = new ArrayList<Computer>();
	
	
	public Collection<Computer> getComputers()
	{
		return computers;
	}
	public void setComputers(Collection<Computer> computers)
	{
		this.computers = computers;
	}
	
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
	public boolean getLicenseRequired()
	{
		return licenseRequired;
	}
	
	public void setLicenseRequired(boolean licenseRequired)
	{
		this.licenseRequired = licenseRequired;
	}
	
}
