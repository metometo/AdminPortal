package com.managedbeans.applications_tab;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.primefaces.event.SelectEvent;

import com.entities_and_database.Application;
import com.entities_and_database.Computer;
import com.entities_and_database.GetSessionFactory;
import com.managedbeans.TableActiveTabManager;

@ManagedBean(name="applicationsData")
@SessionScoped
public class ApplicationsTable	implements Serializable//, SelectableDataModel<User>
{
	//private List<User> selectedUsers;
	
	private List<String> computersInstalledOn = new ArrayList<String>();

	private List<Computer> allComputers = new ArrayList<Computer>();	// list of all registered computers in the database	
	private Application selectedApplication;// = new Application();
	private int selectedApplicationId;
	private List<Application> applications = new ArrayList<Application>();
	
	@ManagedProperty(value = "#{tableActiveTabManager}")
	TableActiveTabManager tableActiveTabManager;

	

	public List<String> getComputersInstalledOn()
	{
		return computersInstalledOn;
	}

	public void setComputersInstalledOn(List<String> computersInstalledOn)
	{
		this.computersInstalledOn = computersInstalledOn;
	}

	public List<Computer> getAllComputers()
	{
		return allComputers;
	}

	public void setAllComputers(List<Computer> allComputers)
	{
		this.allComputers = allComputers;
	}

	public TableActiveTabManager getTableActiveTabManager()
	{
		return tableActiveTabManager;
	}

	public void setTableActiveTabManager(TableActiveTabManager tableActiveTabManager)
	{
		this.tableActiveTabManager = tableActiveTabManager;
	}

	public ApplicationsTable()
	{
		// load users data from the databse
		loadApplicationsFromDatabase();		
	}

	@PostConstruct
    public void init()
    {
         // load users data from the databse
		loadApplicationsFromDatabase();
    }	
	
	
	public int getSelectedApplicationId()
	{
		return selectedApplicationId;
	}



	public void setSelectedApplicationId(int selectedApplicationId)
	{
		this.selectedApplicationId = selectedApplicationId;
	}


	public void setApplications(List<Application> applications)
	{
		this.applications = applications;
	}



	public List<Application> getApplications()
	{
		return applications;
	}

	public Application getSelectedApplication()
	{
		return selectedApplication;
	}

	public void setSelectedApplication(Application selectedApplication)
	{
		this.selectedApplication = selectedApplication;
	}

	/**
	 * Reload the users from the database - update the table in /admin/index.xhtml
	 */
	public void loadApplicationsFromDatabase()
	{
		
		SessionFactory sessionFactory = GetSessionFactory.getInstance();
		Session session = sessionFactory.openSession();//getCurrentSession();//openSession();

		session.beginTransaction();

		SQLQuery query = session.createSQLQuery("select * from application s");
		query.addEntity(com.entities_and_database.Application.class);
		
		
		applications = query.list();
		
		Hibernate.initialize(applications);
		
		// force computers to be loaded from the database (now LAZY loading is ussed, so we need to call the List to initialize it)
		for (int i = 0; i < applications.size(); i++)
		{
			//ArrayList<Computer> computerArray = new ArrayList<Computer>();
			
			Application a = applications.get(i);
			
			Hibernate.initialize(a.getComputers());	
		}
	
		session.getTransaction().commit();
			
		session.close();
		
		//System.out.println("computers: "+computers);
	}
	 
	public void onRowSelect(SelectEvent event) 
	{
//	        FacesMessage msg = new FacesMessage("Computer Selected", ((Computer) event.getObject()).getName());
//	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        
	        Application selApplication = ((Application) event.getObject());
	        
	    	
	        selectedApplicationId = selApplication.getId();
	        
	        for (int i = 0; i < applications.size(); i++)
			{
	        	Application a = applications.get(i); 
				if(a.getId()==selApplication.getId())
				{
					selectedApplication = a;
					
					tableActiveTabManager.setActiveTab(TableActiveTabManager.APPLICATIONS_TAB);
					break;
				}
			}
	        
	        try
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect("./index.jsf");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          
	    }
}
