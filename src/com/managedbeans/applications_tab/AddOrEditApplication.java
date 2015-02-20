package com.managedbeans.applications_tab;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.entities_and_database.Application;
import com.entities_and_database.GetSessionFactory;
import com.managedbeans.TableActiveTabManager;

@ManagedBean(name = "addOrEditApplication")
@SessionScoped
public class AddOrEditApplication implements Serializable
{
	private Application application;
	
	@ManagedProperty(value = "#{applicationsData}")
	ApplicationsTable applicationsData;
	
	@ManagedProperty(value = "#{tableActiveTabManager}")
	TableActiveTabManager tableActiveTabManager;

	
	public AddOrEditApplication()
	{
		application = new Application();
	}
	
	
	public void setApplicationsData(ApplicationsTable applicationsData)
	{
		this.applicationsData = applicationsData;
	}

	public Application getApplication()
	{
		if(application==null)
			application = new Application();
		return application;
	}

	public void setApplication(Application application)
	{
		this.application = application;
	}

	public TableActiveTabManager getTableActiveTabManager()
	{
		return tableActiveTabManager;
	}

	public void setTableActiveTabManager(TableActiveTabManager tableActiveTabManager)
	{
		this.tableActiveTabManager = tableActiveTabManager;
	}
	
	public ApplicationsTable getApplicationsData()
	{
		return applicationsData;
	}

	public void setApplicationssData(ApplicationsTable applicationsData)
	{
		this.applicationsData = applicationsData;
	}
	
	public String registerNewApplication()
	{
		tableActiveTabManager.setActiveTab(TableActiveTabManager.APPLICATIONS_TAB);
		
		try
		{
			SessionFactory sessionFactory = GetSessionFactory.getInstance();
			Session session = sessionFactory.openSession();//getCurrentSession();//openSession();
	
			session.beginTransaction();
			//com.entities.User userEntity = HibernateCommonMethods.getUserbyUsername(userName, session);// (com.entities.User)
				
			session.save(application);//OrUpdate(u);
			
			session.getTransaction().commit();
			
			session.close();
			
			// user registered
			// Update the /admin/index.xhtml table 
			// TODO: do not refresh always, only when the page is /admin/index.xhtml (when the page is registration.xhtml - do not refres)
			applicationsData.loadApplicationsFromDatabase();
			
			
			// invalidate session - clear all session saved variables
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			
			tableActiveTabManager.setActiveIndex(TableActiveTabManager.APPLICATIONS_TAB);
			
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

	
	
	public String editApplication()
	{
		tableActiveTabManager.setActiveTab(TableActiveTabManager.APPLICATIONS_TAB);
		
		try
		{
			SessionFactory sessionFactory = GetSessionFactory.getInstance();
			Session session = sessionFactory.openSession();//getCurrentSession();//openSession();
	
			session.beginTransaction();
			//com.entities.User userEntity = HibernateCommonMethods.getUserbyUsername(userName, session);// (com.entities.User)
			
			//User u = HibernateCommonMethods.getUserbyUsername(usersData.getSelectedUser().getFirstName(), session);
			Application a = (Application) session.load(Application.class, applicationsData.getSelectedApplicationId());
			
			if(a==null)
				return "user_notselected";
			
			session.getTransaction().commit();
			
			
			session.beginTransaction();
			
			
			// name can not be changed
			a.setLicenseRequired(application.getLicenseRequired());
			a.setVendorName(application.getVendorName());
			
			
			
			//u.setUserName(newUsername);	// user name do not change
			//c.setPassword(newPassword);
			//u.setRole(role);
			
//			ArrayList<Role> roles = new ArrayList<Role>();
//			Role r = new Role();
			
			
//			
//			roles.add(r);
//			u.setRoles(roles);			
			
			session.update(a);//OrUpdate(u);

			session.getTransaction().commit();
			
			session.close();
			
			// User updated
			// Update the table
			applicationsData.loadApplicationsFromDatabase();
			
			// invalidate session - clear all session saved variables
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			tableActiveTabManager.setActiveTab(TableActiveTabManager.APPLICATIONS_TAB);
			
			return null;//"user_updated";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();			
		}
		
		return "user_updated_error";
	}
	
	
	 public String deleteSelectedAppliction()
	 {
		// System.out.println(selectedApplicationId);
		 for (int i = 0; i < applicationsData.getApplications().size(); i++)
		 { 
			 //System.out.println(selectedComputerId + " - "+computers.get(i).getId());
			 if(applicationsData.getSelectedApplicationId() == applicationsData.getApplications().get(i).getId())
			 {
				 //users.remove(i);
				 
				 // remove from database and reload page
				 try
				 {
					 SessionFactory sessionFactory = GetSessionFactory.getInstance();
					 Session session = sessionFactory.openSession();//getCurrentSession();//openSession();
			
					 session.beginTransaction();
					 
					 session.delete(applicationsData.getApplications().get(i));//OrUpdate(u);
					
					 session.getTransaction().commit();
					
					 session.close();
				
					 
					 // user deleted from the database
					 // Update the table
					 applicationsData.loadApplicationsFromDatabase();
					 
					 return null;
					 
					 
				}
				catch(Exception ex)
				{
					ex.printStackTrace();			
				}
				
				return "deleting_error";
				 
				// break;
			 }
		 }
		 
		 return "deleting_error";
	 }
}