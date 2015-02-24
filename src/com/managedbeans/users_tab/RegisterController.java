package com.managedbeans.users_tab;

import java.io.Serializable;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.common_classes.FormMethods;
import com.entities.User;
import com.entities.helpers.GetSessionFactory;
import com.entities.helpers.HibernateCommonMethods;
import com.entities.helpers.RoleType;

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
	
		SessionFactory sessionFactory = GetSessionFactory.getInstance();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if( userAlreadyRegistered(sessionFactory) )
		{
			// add new message saing that the user is registered
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "This username is already taken.");
			facesContext.addMessage(FormMethods.findElementId("username"), facesMessage);
			
			return null;
		}
		
		
		
		// if user is not taken - register it (save it)
		if( saveUserToDatabase(sessionFactory) )
		{
			// add new message saing that the user is registered
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "You are now registered.", "User is registered.");
			facesContext.addMessage(null, facesMessage);
			
			// clear session data, otherwise the user will be displayed in add user dualog in /admin page
			facesContext.getExternalContext().invalidateSession();	
			
			// user registered
			// Update the /admin/index.xhtml table 
			// TODO: do not refresh always, only when the page is /admin/index.xhtml (when the page is registration.xhtml - do not refres)
			usersData.loadUsersFromDatabase();
			
			return "registered";
			
		}
		else
		{
			// error occured while saving
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error occurred, please try again later.", "");
			facesContext.addMessage(null, facesMessage);
			
			return null;
		}
		
	}

	private boolean saveUserToDatabase(SessionFactory sessionFactory)
	{
		
		Session session = sessionFactory.getCurrentSession();//openSession();
		try
		{
			session.beginTransaction();
			
			// crate User object and sava it to the database
			User u = new User();
			u.setName(firstName);
			u.setLastName(lastName);
			u.setUserName(username);
			u.setPassword(password);
			
			
			if(adminRole)
			{	
				u.setRole(RoleType.ROLE_ADMIN);
			}
			else
			{
				u.setRole(RoleType.ROLE_USER);
			}
			
					
			session.save(u);
			session.getTransaction().commit();
			
			if(session.isOpen())
				session.close();
			
			
			return true;
		}
		catch(Exception ex)
		{
			
			return false;
		}
		
	}

	/**
	 * Check the database if given username is already registered
	 */
	public boolean userAlreadyRegistered(SessionFactory sessionFactory)
	{
		
		Session session = sessionFactory.getCurrentSession();//openSession();
		try
		{
			session.beginTransaction();
			
			User u = HibernateCommonMethods.getUserbyUsername(username, session);
			
			session.getTransaction().commit();
			
			if(session.isOpen())
				session.close();
			
			if(u!=null)
			{
				
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
}