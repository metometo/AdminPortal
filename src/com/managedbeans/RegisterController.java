package com.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.entities.GetSessionFactory;
import com.entities.Role;
import com.entities.User;

@ManagedBean(name = "registerController")
@RequestScoped
public class RegisterController implements Serializable
{
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private boolean adminRole;
	
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
			
			ArrayList<Role> roles = new ArrayList<Role>();
			Role r = new Role();
			
			// TODO: REFACTOR TO USE THE CREATED ROLES INSTEAD OF CREATING NEW WITH THE SAME NAME
			if(adminRole)
			{
				r.setUserRole("ROLE_ADMIN");	
			}
			else
			{
				r.setUserRole("ROLE_USER");	
			}
			
			roles.add(r);
			u.setRoles(roles);
			
			session.saveOrUpdate(u);
			
			session.getTransaction().commit();
			
			session.close();
			
			return "registered";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();			
		}
		
		return "error";
	}

}