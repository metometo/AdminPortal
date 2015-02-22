package com.managedbeans.computers_tab;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.primefaces.event.SelectEvent;
import com.entities.Computer;
import com.entities.helpers.GetSessionFactory;
import com.managedbeans.TableActiveTabManager;

@ManagedBean(name="computersData")
@SessionScoped
public class ComputersTable	implements Serializable//, SelectableDataModel<User>
{
	private Computer selectedComputer = new Computer();
	private int selectedComputerId;
	private List<Computer> computers = new ArrayList<Computer>();
	
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

	public ComputersTable()
	{
		// load users data from the databse
		loadComputersFromDatabase();
	}
	
	
	
	public int getSelectedComputerId()
	{
		return selectedComputerId;
	}



	public void setSelectedComputerId(int selectedComputerId)
	{
		this.selectedComputerId = selectedComputerId;
	}



	public List<Computer> getComputers()
	{
		return computers;
	}



	public void setComputers(List<Computer> computers)
	{
		this.computers = computers;
	}



	public Computer getSelectedComputer()
	{
		return selectedComputer;
	}



	/**
	 * Reload the users from the database - update the table in /admin/index.xhtml
	 */
	public void loadComputersFromDatabase()
	{
		SessionFactory sessionFactory = GetSessionFactory.getInstance();
		Session session = sessionFactory.openSession();//getCurrentSession();//openSession();

		session.beginTransaction();

		SQLQuery query = session.createSQLQuery("select * from computer s");
		query.addEntity(com.entities.Computer.class);
		
		
		computers = query.list();
		
		session.getTransaction().commit();
		
		session.close();
	}

	
	 public String deleteSelectedComputer()
	 {
		 System.out.println(selectedComputerId);
		 for (int i = 0; i < computers.size(); i++)
		 { 
			 System.out.println(selectedComputerId + " - "+computers.get(i).getId());
			 if(selectedComputerId == computers.get(i).getId())
			 {
				 // remove from database and reload page
				 try
				 {
					 SessionFactory sessionFactory = GetSessionFactory.getInstance();
					 Session session = sessionFactory.openSession();
			
					 session.beginTransaction();
					 
					 session.delete(computers.get(i));
					
					 session.getTransaction().commit();
					
					 session.close();
				
					 
					 // user deleted from the database
					 // Update the table
					 loadComputersFromDatabase();
					 
					 return null;
				}
				catch(Exception ex)
				{
					ex.printStackTrace();			
				}
				
				return "deleting_error";
			 }
		 }
		 
		 return "deleting_error";
	 }

	 public void onRowSelect(SelectEvent event) 
	 {	        
	        Computer selComputer = ((Computer) event.getObject());
	        
	    	
	        selectedComputerId = selComputer.getId();
	        
	        for (int i = 0; i < computers.size(); i++)
			{
	        	Computer c = computers.get(i); 
				if(c.getId()==selComputer.getId())
				{
					selectedComputer = c;
					tableActiveTabManager.setActiveTab(TableActiveTabManager.COMPUTERS_TAB);
					break;
				}
			}
	        
	        try
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect("./index.jsf");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
    }
	 
	public void setSelectedComputer(Computer selectedComputer)
	{
			this.selectedComputer = selectedComputer;

	}
}
