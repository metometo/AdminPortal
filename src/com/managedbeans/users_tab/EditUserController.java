package com.managedbeans.users_tab;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.entities.User;
import com.entities.helpers.GetSessionFactory;
import com.entities.helpers.RoleType;
import com.managedbeans.TableActiveTabManager;

@ManagedBean(name = "updateUserController")
@RequestScoped
public class EditUserController implements Serializable
{
	private String newFirstName;
	private String newLastName;
	private String newUsername;
	private String newPassword;
	private boolean newAdminRole;

	@ManagedProperty(value = "#{usersData}")
	UsersTable usersData;
	
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

	public UsersTable getUsersData()
	{
		return usersData;
	}

	public void setUsersData(UsersTable usersData)
	{
		this.usersData = usersData;
	}

	public String getNewFirstName()
	{
		return newFirstName;
	}

	public void setNewFirstName(String newFirstName)
	{
		this.newFirstName = newFirstName;
	}

	public String getNewLastName()
	{
		return newLastName;
	}

	public void setNewLastName(String newLastName)
	{
		this.newLastName = newLastName;
	}

	public String getNewUsername()
	{
		return newUsername;
	}

	public void setNewUsername(String newUsername)
	{
		this.newUsername = newUsername;
	}

	public String getNewPassword()
	{
		return newPassword;
	}

	public void setNewPassword(String newPassword)
	{
		this.newPassword = newPassword;
	}

	public boolean isNewAdminRole()
	{
		return newAdminRole;
	}

	public void setNewAdminRole(boolean newAdminRole)
	{
		this.newAdminRole = newAdminRole;
	}

	public String editNewUser()
	{
		try
		{
			SessionFactory sessionFactory = GetSessionFactory.getInstance();
			Session session = sessionFactory.openSession();//getCurrentSession();//openSession();
	
			session.beginTransaction();
			
			User u = (User) session.load(User.class, usersData.getSelectedUserId());
			
			if(u==null)
				return "user_notselected";
			
			session.getTransaction().commit();
			
			
			session.beginTransaction();
			
			// crate User object and sava it to the database						
			u.setName(newFirstName);
			u.setLastName(newLastName);
			//u.setUserName(newUsername);	// user name - do not change
			u.setPassword(newPassword);
			
			if(newAdminRole)
			{
				u.setRole(RoleType.ROLE_ADMIN);	
			}
			else
			{
				u.setRole(RoleType.ROLE_USER);	
			}

			session.update(u);

			session.getTransaction().commit();
			
			session.close();
			
			// User updated
			// Update the table
			usersData.loadUsersFromDatabase();
			
			tableActiveTabManager.setActiveTab(TableActiveTabManager.USERS_TAB);
			
			return "user_updated";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();			
		}
		
		return "user_updated_error";
	}
}