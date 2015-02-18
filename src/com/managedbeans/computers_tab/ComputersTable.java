package com.managedbeans.computers_tab;

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

import com.entities_and_database.Computer;
import com.entities_and_database.GetSessionFactory;
import com.managedbeans.TableActiveTabManager;

@ManagedBean(name="computersData")
@SessionScoped
public class ComputersTable	implements Serializable//, SelectableDataModel<User>
{
	//private List<User> selectedUsers;
	
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
		query.addEntity(com.entities_and_database.Computer.class);
		
		
		computers = query.list();
		
		session.getTransaction().commit();
		
		session.close();
		
		//System.out.println("computers: "+computers);
	}

	
	 public String deleteSelectedComputer()
	 {
		 System.out.println(selectedComputerId);
		 for (int i = 0; i < computers.size(); i++)
		 { 
			 System.out.println(selectedComputerId + " - "+computers.get(i).getId());
			 if(selectedComputerId == computers.get(i).getId())
			 {
				 //users.remove(i);
				 
				 // remove from database and reload page
				 try
				 {
					 SessionFactory sessionFactory = GetSessionFactory.getInstance();
					 Session session = sessionFactory.openSession();//getCurrentSession();//openSession();
			
					 session.beginTransaction();
					 
					 session.delete(computers.get(i));//OrUpdate(u);
					
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
				 
				// break;
			 }
		 }
		 
		 return "deleting_error";
	 }
	 
//	 public void setSelectedCars(List<User> selectedUsers) {
//	        this.selectedUsers = selectedUsers;
//	        System.out.println("Selected Users: "+selectedUsers);
//	    }
	     
	    public void onRowSelect(SelectEvent event) {
	        FacesMessage msg = new FacesMessage("Computer Selected", ((Computer) event.getObject()).getName());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        
	        Computer selComputer = ((Computer) event.getObject());
	        
	    	
	        selectedComputerId = selComputer.getId();
	        
	        for (int i = 0; i < computers.size(); i++)
			{
	        	Computer c = computers.get(i); 
				if(c.getId()==selComputer.getId())
				{
					selectedComputer = c;
					//System.out.println("User Selected: " + selectedUser.getUserName() + ", id: " + selectedUserId);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
//	    	FacesMessage msg = new FacesMessage("Car Selected", ((User) event.getObject()).getId()+"");  
//	    	  
//	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	    }
	 
//	    public void onRowUnselect(UnselectEvent event) {
//	        FacesMessage msg = new FacesMessage("User Unselected", ((User) event.getObject()).getUserName());
//	        FacesContext.getCurrentInstance().addMessage(null, msg);
//	        
//	        System.out.println("User unselected: " + ((User) event.getObject()).getUserName());
//	    }

//		public Computer getSelectedUser()
//		{			
//			return selectedComputer;
//		}

		public void setSelectedComputer(Computer selectedComputer)
		{
			this.selectedComputer = selectedComputer;

		}

//		@Override
//		public User getRowData(String arg0)
//		{			
//			System.out.println("row data: "+arg0);
//			
//			return users.get(0);
//		}
//
//		@Override
//		public Object getRowKey(User arg0)
//		{
//			System.out.println("row key: "+arg0.getUserName());
//			
//			return users.get(0);
//		}
	
//		 public void login(ActionEvent event) {
//		        RequestContext context = RequestContext.getCurrentInstance();
//		        FacesMessage message = null;
//		        boolean loggedIn = false;
//		         
////		        if(username != null && username.equals("admin") && password != null && password.equals("admin")) {
////		            loggedIn = true;
////		            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
////		        } else {
////		            loggedIn = false;
////		            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
////		        }
//		        
//		        //System.out.println(selectedUser);
//		        
//		        FacesContext.getCurrentInstance().addMessage(null, message);
//		        context.addCallbackParam("loggedIn", loggedIn);
//		    } 
//		 
		 
		 
//		 public void onRowEdit(RowEditEvent event) {
//		        FacesMessage msg = new FacesMessage("Car Edited", ((User) event.getObject()).getId()+"");
//		        FacesContext.getCurrentInstance().addMessage(null, msg);
//		        
//		        System.out.println("row edit: " + ((User) event.getObject()));
//		    }
//		     
//		    public void onRowCancel(RowEditEvent event) {
//		        FacesMessage msg = new FacesMessage("Edit Cancelled", ((User) event.getObject()).getId()+"");
//		        FacesContext.getCurrentInstance().addMessage(null, msg);
//		        
//		        System.out.println("row edit canceled: " + ((User) event.getObject()));
//		    }
		     
//		    public void onCellEdit(CellEditEvent event) {
//		        Object oldValue = event.getOldValue();
//		        Object newValue = event.getNewValue();
//		         
//		        if(newValue != null && !newValue.equals(oldValue)) {
//		            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
//		            FacesContext.getCurrentInstance().addMessage(null, msg);
//		      
//		        
//		        System.out.println("Cell Changed: Old: " + oldValue + ", New:" + newValue);
//		        System.out.println("selected user: "+selectedUserId);
//		        
////		        for (int i = 0; i < users.size(); i++)
////				{
////		        	if(users.get(i).getId()==selectedUserId)
////		            {
////		        		System.out.println("user: "+users.get(i));
////		        		break;
////		            }
////		        	
////				}
//		        
//		        }
//		    
//		    }
//		    
//		    public void rowEdit(RowEditEvent ev){
//		        
//		        try {
//		            User car = (User)ev.getObject();
////		            CarJpaController ctrlCar = new CarJpaController();
////
////		            ctrlCar.edit(car);
//		            System.out.println("Row edit: " + car);
//
//		        } catch (Exception e) {
//		            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRORE", e.toString()));
//		        }
//		    }
}
