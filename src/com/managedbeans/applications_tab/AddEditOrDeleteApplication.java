package com.managedbeans.applications_tab;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.entities.Application;
import com.entities.Computer;
import com.entities.helpers.GetSessionFactory;
import com.managedbeans.PageBean;
import com.managedbeans.TableActiveTabManager;

@ManagedBean(name = "addEditOrDeleteApplication")
@SessionScoped
public class AddEditOrDeleteApplication implements Serializable
{
	private Application newApplication;	// variable for the new application dialog
	
	@ManagedProperty(value = "#{applicationsData}")
	ApplicationsTable applicationsData;
	
	@ManagedProperty(value = "#{tableActiveTabManager}")
	TableActiveTabManager tableActiveTabManager;

	
	public AddEditOrDeleteApplication()
	{
		newApplication = new Application();
	}
	
	
	public void setApplicationsData(ApplicationsTable applicationsData)
	{
		this.applicationsData = applicationsData;
	}

	public Application getNewApplication()
	{
		if(newApplication==null)
			newApplication = new Application();
		
		return newApplication;
	}

	public void setNewApplication(Application newApplication)
	{
		this.newApplication = newApplication;
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
		
		SessionFactory sessionFactory = GetSessionFactory.getInstance();
		Session session = sessionFactory.getCurrentSession();//openSession();

		try
		{
			session.beginTransaction();	
			
			newApplication.getComputers().clear();
			
			// get all computers, to link them with the application
			for (int i = 0; i < applicationsData.getComputersInstalledOn().size(); i++)
			{
				
				//session.beginTransaction();
		
				SQLQuery query = session.createSQLQuery("select * from computer s  WHERE computer_id = :computer_id");
				query.addEntity(com.entities.Computer.class);
				query.setParameter("computer_id", applicationsData.getComputersInstalledOn().get(i));
				
				
				
				List<Computer> c = query.list();
				
				//session.getTransaction().commit();
				
//				session.close();
//				session = sessionFactory.getCurrentSession();
//				session.beginTransaction();	
				
				if(c.size()==1)
				{
					newApplication.getComputers().add(c.get(0));// add computer to the application
					//newApplication.getComputers().clear();
				}
				else if(c.size()>1)
				{
					//new AssertionError("It is impossible two computers to have same ID");
				}
			}
			
			
			//session.beginTransaction();
			session.save(newApplication);//application);//OrUpdate(u);
			
			//if (!session.getTransaction().wasCommitted())
					session.getTransaction().commit();
			
			if(session.isOpen())
				session.close();
			
			// user registered
			// Update the /admin/index.xhtml table 
			// TODO: do not refresh always, only when the page is /admin/index.xhtml (when the page is registration.xhtml - do not refres)
			
			// invalidate session - clear all session saved variables
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			
			applicationsData.loadApplicationsFromDatabase();
			
			tableActiveTabManager.setActiveIndex(TableActiveTabManager.APPLICATIONS_TAB);
			
			//System.out.println(applicationsData.getComputersInstalledOn());
			
			return "application_registered";
		}
		catch(Exception ex)
		{
			
			if(session.isOpen())
				session.close();
			
		}
		
		return "application_registered_error";
	}

	
	
	public String editApplication()
	{
		tableActiveTabManager.setActiveTab(TableActiveTabManager.APPLICATIONS_TAB);
		
		
		SessionFactory sessionFactory = GetSessionFactory.getInstance();
		Session session = sessionFactory.getCurrentSession();//openSession();
		
		try
		{
			session.beginTransaction();
			//com.entities.User userEntity = HibernateCommonMethods.getUserbyUsername(userName, session);// (com.entities.User)
			
			//User u = HibernateCommonMethods.getUserbyUsername(usersData.getSelectedUser().getFirstName(), session);
			Application a = (Application) session.load(Application.class, applicationsData.getSelectedApplicationId());
			
			if(a==null)
				return "application_notselected";
			
			if(a.getComputers()!=null)
				Hibernate.initialize(a.getComputers());
			
			session.getTransaction().commit();
			
			if(a!=null)
			{
				
				session = sessionFactory.getCurrentSession();//openSession();
				session.beginTransaction();
				
				if(a.getComputers()!=null)
					a.getComputers().clear();	// remove old computers
				
				// get all computers, to link them with the application
				for (int i = 0; i < applicationsData.getComputersInstalledOn().size(); i++)
				{
					
					//session.beginTransaction();
			
					SQLQuery query = session.createSQLQuery("select * from computer s  WHERE computer_id = :computer_id");
					query.addEntity(com.entities.Computer.class);
					query.setParameter("computer_id", applicationsData.getComputersInstalledOn().get(i));
					
					
					List<Computer> c = query.list();	// indeed only one computer is returned
					
					if(c.size()==1)
					{
						a.getComputers().add(c.get(0));// add computer to the application
					}
					else if(c.size()>1)
					{
						//new AssertionError("It is impossible two computers to have same ID");
					}
				}
				
				// name can not be changed, add other data
				a.setLicenseRequired(applicationsData.getSelectedApplication().getLicenseRequired());//application.getLicenseRequired());
				a.setVendorName(applicationsData.getSelectedApplication().getVendorName());//.getVendorName());	// vendor name
				
				
				
				session.update(a);//OrUpdate(u);
	
				session.getTransaction().commit();
				
			}
			
			if(session.isOpen())
				session.close();
			
			// User updated
			// Update the table
			applicationsData.loadApplicationsFromDatabase();
			
			// invalidate session - clear all session saved variables
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			tableActiveTabManager.setActiveTab(TableActiveTabManager.APPLICATIONS_TAB);
			
			return "application_updated";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
			if(session.isOpen())
				session.close();
		}
		
		return "application_updated_error";
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
				 
				 SessionFactory sessionFactory = GetSessionFactory.getInstance();
				 Session session = sessionFactory.getCurrentSession();//openSession();
				
				 try
				 {
					 session.beginTransaction();
					 
					 session.delete(applicationsData.getApplications().get(i));//OrUpdate(u);
					
					 session.getTransaction().commit();
					
					 if(session.isOpen())
							session.close();
				
					 
					 FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
					 
					 
					 // user deleted from the database
					 // Update the table
					 applicationsData.loadApplicationsFromDatabase();
					 
					 return "deleted";
					 
					 
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					
					 if(session.isOpen())
							session.close();
				}
				
				return "deleting_error";
				 
				// break;
			 }
		 }
		 
		 return "deleting_error";
	 }
}