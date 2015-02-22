package com.managedbeans.computers_tab;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.entities.Computer;
import com.entities.User;
import com.entities.helpers.GetSessionFactory;
import com.entities.helpers.HibernateCommonMethods;
import com.entities.helpers.Role;
import com.managedbeans.TableActiveTabManager;
import com.managedbeans.users_tab.UsersTable;

@ManagedBean(name = "registerComputer")
@SessionScoped
public class RegisterComputer implements Serializable
{
	private String name;
	private String ip;
	private String login;
	private String password;
	
//	private String firstName;
//	private String lastName;
//	private String username;
//	private String password;
//	private boolean adminRole;
	
	@ManagedProperty(value = "#{computersData}")
	ComputersTable computersData;
	
	@ManagedProperty(value = "#{tableActiveTabManager}")
	TableActiveTabManager tableActiveTabManager;

	public TableActiveTabManager getTableActiveTabManager()
	{
		return tableActiveTabManager;
	}

	public void setTableActiveTabManager(TableActiveTabManager tableActiveTabManager)
	{
		this.tableActiveTabManager = tableActiveTabManager;
	}
	
	public ComputersTable getComputersData()
	{
		return computersData;
	}

	public void setComputersData(ComputersTable computersData)
	{
		this.computersData = computersData;
	}

	
	

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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

	
	
	
	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public String registerNewComputer()
	{
		tableActiveTabManager.setActiveTab(TableActiveTabManager.COMPUTERS_TAB);
		
		try
		{
			SessionFactory sessionFactory = GetSessionFactory.getInstance();
			Session session = sessionFactory.openSession();//getCurrentSession();//openSession();
	
			session.beginTransaction();
			//com.entities.User userEntity = HibernateCommonMethods.getUserbyUsername(userName, session);// (com.entities.User)
				
			// crate User object and sava it to the database
			Computer c = new Computer();
			c.setIp(ip);
			c.setLogin(login);
			c.setName(name);
			c.setPassword(password);
			
			
			
			
			
			session.save(c);//OrUpdate(u);
			
			session.getTransaction().commit();
			
			session.close();
			
			// user registered
			// Update the /admin/index.xhtml table 
			// TODO: do not refresh always, only when the page is /admin/index.xhtml (when the page is registration.xhtml - do not refres)
			computersData.loadComputersFromDatabase();
			
			
			// invalidate session - clear all session saved variables
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			
			
			return "registered";
		}
		catch(Exception ex)
		{
			// check if the user exyst - duplicated enrty is for the column username
			if(ex.getCause().toString().contains("Duplicate entry"))
				return "user_exist";
					
		}
		
		return "error";
	}

}