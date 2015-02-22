package com.managedbeans.computers_tab;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.entities.Computer;
import com.entities.helpers.GetSessionFactory;
import com.managedbeans.TableActiveTabManager;

@ManagedBean(name = "editComputer")
@RequestScoped
public class EditComputer implements Serializable
{
	private String newName;
	private String newIP;
	private String newLogin;
	private String newPassword;
	
	
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



	public ComputersTable getUsersData()
	{
		return computersData;
	}



	public void setUsersData(ComputersTable usersData)
	{
		this.computersData = usersData;
	}

	public String getNewName()
	{
		return newName;
	}

	public void setNewName(String newName)
	{
		this.newName = newName;
	}


	public String getNewIP()
	{
		return newIP;
	}

	public void setNewIP(String newIP)
	{
		this.newIP = newIP;
	}

	public String getNewLogin()
	{
		return newLogin;
	}

	public void setNewLogin(String newLogin)
	{
		this.newLogin = newLogin;
	}

	public String getNewPassword()
	{
		return newPassword;
	}

	public void setNewPassword(String newPassword)
	{
		this.newPassword = newPassword;
	}

	public ComputersTable getComputersData()
	{
		return computersData;
	}

	public void setComputersData(ComputersTable computersData)
	{
		this.computersData = computersData;
	}

	public String editComputer()
	{
		tableActiveTabManager.setActiveTab(TableActiveTabManager.COMPUTERS_TAB);
		
		
		try
		{
			SessionFactory sessionFactory = GetSessionFactory.getInstance();
			Session session = sessionFactory.openSession();//getCurrentSession();//openSession();
	
			session.beginTransaction();
			Computer c = (Computer) session.load(Computer.class, computersData.getSelectedComputerId());
			
			if(c==null)
				return "user_notselected";
			
			session.getTransaction().commit();
			
			
			session.beginTransaction();
			
			// crate User object and save it to the database						
			c.setIp(newIP);
			c.setLogin(newLogin);
			//c.setName(newName);	// do not change name
			c.setPassword(newPassword);
			
			session.update(c);
			
			session.getTransaction().commit();
			
			session.close();
			
			// User updated
			// Update the table
			computersData.loadComputersFromDatabase();
			
			return "user_updated";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();			
		}
		
		return "user_updated_error";
	}
}