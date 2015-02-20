package com.managedbeans.applications_tab;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.primefaces.event.SelectEvent;

import com.entities_and_database.Application;

import com.entities_and_database.GetSessionFactory;
import com.managedbeans.TableActiveTabManager;

@ManagedBean(name="applicationsData")
@SessionScoped
public class ApplicationsTable	implements Serializable//, SelectableDataModel<User>
{
	//private List<User> selectedUsers;
	
	private Application selectedApplication = new Application();
	private int selectedApplicationId;
	private List<Application> applications = new ArrayList<Application>();
	
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

	public ApplicationsTable()
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
