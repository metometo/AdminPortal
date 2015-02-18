package com.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.entities.GetSessionFactory;
import com.entities.HibernateCommonMethods;
import com.entities.Role;
import com.entities.User;

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
			//com.entities.User userEntity = HibernateCommonMethods.getUserbyUsername(userName, session);// (com.entities.User)
			
			//User u = HibernateCommonMethods.getUserbyUsername(usersData.getSelectedUser().getFirstName(), session);
			User u = (User) session.load(User.class, usersData.getSelectedUserId());
			
			if(u==null)
				return "user_notselected";
			
			session.getTransaction().commit();
			
			
			session.beginTransaction();
			
			// crate User object and sava it to the database
						
			u.setFirstName(newFirstName);
			u.setLastName(newLastName);
			//u.setUserName(newUsername);	// user name do not change
			u.setPassword(newPassword);
			
			ArrayList<Role> roles = new ArrayList<Role>();
			Role r = new Role();
			
			// TODO: REFACTOR TO USE THE CREATED ROLES INSTEAD OF CREATING NEW WITH THE SAME NAME
			if(newAdminRole)
			{
				r.setUserRole("ROLE_ADMIN");	
			}
			else
			{
				r.setUserRole("ROLE_USER");	
			}
			
			roles.add(r);
			u.setRoles(roles);			
			
			session.update(u);//OrUpdate(u);

			session.getTransaction().commit();
			
			session.close();
			
			// User updated
			// Update the table
			usersData.loadUsersFromDatabase();
			
			return "user_updated";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();			
		}
		
		return "user_updated_error";
	}
}