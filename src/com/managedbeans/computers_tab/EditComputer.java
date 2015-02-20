package com.managedbeans.computers_tab;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.entities_and_database.Computer;
import com.entities_and_database.GetSessionFactory;
import com.managedbeans.TableActiveTabManager;

@ManagedBean(name = "editComputer")
@RequestScoped
public class EditComputer implements Serializable
{
	private String newName;
	private String newIP;
	private String newLogin;
	private String newPassword;
	
	
	//private String newFirstName;
	//private String newLastName;
	//private String newUsername;
	//private String newPassword;
	//private boolean newAdminRole;
	//private String role;
	

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
			//com.entities.User userEntity = HibernateCommonMethods.getUserbyUsername(userName, session);// (com.entities.User)
			
			//User u = HibernateCommonMethods.getUserbyUsername(usersData.getSelectedUser().getFirstName(), session);
			Computer c = (Computer) session.load(Computer.class, computersData.getSelectedComputerId());
			
			if(c==null)
				return "user_notselected";
			
			session.getTransaction().commit();
			
			
			session.beginTransaction();
			
			// crate User object and sava it to the database
						
			c.setIp(newIP);
			c.setLogin(newLogin);
			//c.setName(newName);
			c.setPassword(newPassword);
			
			//u.setUserName(newUsername);	// user name do not change
			//c.setPassword(newPassword);
			//u.setRole(role);
			
//			ArrayList<Role> roles = new ArrayList<Role>();
//			Role r = new Role();
			
			
//			
//			roles.add(r);
//			u.setRoles(roles);			
			
			session.update(c);//OrUpdate(u);

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