package com.managedbeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="tableActiveTabManager")
@SessionScoped
public class TableActiveTabManager implements Serializable
{
	public static final int USERS_TAB = 0;
	public static final int COMPUTERS_TAB = 1;
	public static final int APPLICATIONS_TAB = 2;
	
    private int activeIndex;
   
    public void setActiveTab(int tab) 
    {
    	setActiveIndex(tab);
    }

	public int getActiveIndex()
	{
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex)
	{
		this.activeIndex = activeIndex;
	}
    
    
} 