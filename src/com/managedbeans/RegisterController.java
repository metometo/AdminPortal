package com.managedbeans;

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

import com.entities_and_database.GetSessionFactory;
import com.entities_and_database.HibernateCommonMethods;
import com.entities_and_database.Role;
import com.entities_and_database.User;
import com.managedbeans.users_tab.UsersTable;

@ManagedBean(name = "registerController")
@SessionScoped
public class RegisterController implements Serializable
{
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private boolean adminRole;
	
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

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}


	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}



	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}



	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}



	public boolean getAdminRole()
	{
		return adminRole;
	}

	public void setAdminRole(boolean adminRole)
	{
		this.adminRole = adminRole;
	}


	public String registerNewUser()
	{
		try
		{
			SessionFactory sessionFactory = GetSessionFactory.getInstance();
			Session session = sessionFactory.openSession();//getCurrentSession();//openSession();
	
			session.beginTransaction();
			//com.entities.User userEntity = HibernateCommonMethods.getUserbyUsername(userName, session);// (com.entities.User)
				
			// crate User object and sava it to the database
			User u = new User();
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setUserName(username);
			u.setPassword(password);
			
//			ArrayList<Role> roles = new ArrayList<Role>();
//			Role r = new Role();
			
		
			if(adminRole)
			{
				//r.setUserRole("ROLE_ADMIN");	
				u.setRole("ROLE_ADMIN");
			}
			else
			{
				//r.setUserRole("ROLE_USER");
				u.setRole("ROLE_USER");
			}
			
			//roles.add(r);
			//u.setRoles(roles);
			
			
			session.save(u);//OrUpdate(u);
			
			session.getTransaction().commit();
			
			session.close();
			
			// user registered
			// Update the /admin/index.xhtml table 
			// TODO: do not refresh always, only when the page is /admin/index.xhtml (when the page is registration.xhtml - do not refres)
			usersData.loadUsersFromDatabase();
			
			
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