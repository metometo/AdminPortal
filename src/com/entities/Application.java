package com.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.entities.helpers.BaseEntity;

@Entity
@Table(name="application")
public class Application extends BaseEntity implements Serializable
{
	private String vendorName;
	private boolean licenseRequired;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //mappedBy="applications",
	@JoinColumn(unique=false)
	Collection<Computer> computers = new ArrayList<Computer>();
	
	
	public Collection<Computer> getComputers()
	{
		return computers;
	}
	public void setComputers(Collection<Computer> computers)
	{
		this.computers = computers;
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
