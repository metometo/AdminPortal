package com.managedbeans.computers_tab;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.entities.Computer;
import com.entities.helpers.GetSessionFactory;
import com.managedbeans.TableActiveTabManager;

@ManagedBean(name = "registerComputer")
@SessionScoped
public class RegisterComputer implements Serializable
{
	private String name;
	private String ip;
	private String login;
	private String password;

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
			
			// crate User object and save it to the database
			Computer c = new Computer();
			c.setIp(ip);
			c.setLogin(login);
			c.setName(name);
			c.setPassword(password);
			
			
			
			session.save(c);
			
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
			// check if the user exist - duplicated enry is for the column username
			if(ex.getCause().toString().contains("Duplicate entry"))
				return "user_exist";
					
		}
		
		return "error";
	}

}