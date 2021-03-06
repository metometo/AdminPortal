package com.managedbeans;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

import com.common_classes.FormMethods;
import com.entities.helpers.GetSessionFactory;
import com.entities.helpers.HibernateCommonMethods;
import com.entities.helpers.RoleType;

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements PhaseListener
{
	private String user;
	private String password;
	private ArrayList<RoleType> roles=new ArrayList<RoleType>();

	/**
	 *
	 * Redirects the login request directly to spring security check. Leave this
	 * method as it is to properly support spring security.
	 *
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String doLogin() throws ServletException, IOException
	{	
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();

		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/j_spring_security_check");

		
			dispatcher.forward((ServletRequest) context.getRequest(),
					(ServletResponse) context.getResponse());
		
		
		FacesContext.getCurrentInstance().responseComplete();

		getRolesFromDatabase(user);
			
			
		return null;
	}

	public void afterPhase(PhaseEvent event)
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 * 
	 * Do something before rendering phase.
	 */
	public void beforePhase(PhaseEvent event)
	{
		Exception e = (Exception) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap()
				.get(WebAttributes.AUTHENTICATION_EXCEPTION);

		if (e instanceof BadCredentialsException)
		{
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap()
					.put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Username or password not valid.",
							"Username or password not valid"));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 * 
	 * In which phase you want to interfere?
	 */
	public PhaseId getPhaseId()
	{
		return PhaseId.RENDER_RESPONSE;
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public ArrayList<RoleType> getRoles()
	{
		return roles;
	}

	public boolean hasRole(String role)
	{		
		for (int i = 0; i < roles.size(); i++)
		{
			if (roles.get(i).equals(role))
				return true;
		}

		return false;
	}

	private void getRolesFromDatabase(String userName)
	{
		SessionFactory sessionFactory = GetSessionFactory.getInstance();
		Session session = sessionFactory.openSession();//getCurrentSession();//openSession();

		session.beginTransaction();
		com.entities.User userEntity = HibernateCommonMethods.getUserbyUsername(userName, session);
		session.getTransaction().commit();


		roles.clear(); // clear old data from previous session logins
		
		if(userEntity!=null)	// user exists
			roles.add(userEntity.getRole());	// adds all user roles to the class array
		
		session.close();
				
	}

}