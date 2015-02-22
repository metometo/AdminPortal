package com.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.entities.helpers.BaseEntity;

@Entity
@Table(name="application")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "app_id")),
	@AttributeOverride(name = "name", column = @Column(name = "app_name"))
})
public class Application extends BaseEntity implements Serializable
{
	@Column(name="vendor_name")
	private String vendorName;
	
	@Column(name="license_required")
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
