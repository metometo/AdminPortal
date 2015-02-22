package com.managedbeans.users_tab;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.jasper.tagplugins.jstl.core.Remove;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.SelectableDataModel;

import com.entities.User;
import com.entities.helpers.GetSessionFactory;
import com.entities.helpers.HibernateCommonMethods;
import com.entities.helpers.Role;
import com.managedbeans.TableActiveTabManager;

@ManagedBean(name="usersData")
@SessionScoped
public class UsersTable	implements Serializable//, SelectableDataModel<User>
{
	//private List<User> selectedUsers;
	
	private User selectedUser = new User();
	private int selectedUserId;
	private List<User> users = new ArrayList<User>();
	//private ArrayList<ArrayList<String>> userRoles = new ArrayList<ArrayList<String>>();
	
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
	
	public int getSelectedUserId()
	{
		return selectedUserId;
	}

	public void setSelectedUserId(int selectedUserId)
	{
		this.selectedUserId = selectedUserId;
	}

	public UsersTable()
	{
		// load users data from the databse
		loadUsersFromDatabase();
	}
	
	/**
	 * Reload the users from the database - update the table in /admin/index.xhtml
	 */
	public void loadUsersFromDatabase()
	{
		
		SessionFactory sessionFactory = GetSessionFactory.getInstance();
		Session session = sessionFactory.openSession();//getCurrentSession();//openSession();

		session.beginTransaction();

		SQLQuery query = session.createSQLQuery("select * from user s");
		query.addEntity(com.entities.User.class);
		
		
		users = query.list();
		
//		//System.out.println(users.get(0).getRoles());// call roles to pull them from the database
//		for (int i = 0; i < users.size(); i++)
//		{
//			ArrayList<String> singleUserRoles = new ArrayList<String>();
//			
//			for (int j = 0; j < users.get(i).getRoles().size(); j++)
//			{
//				singleUserRoles.add(users.get(i).getRoles().get(j).getAuthority());// call to load the data from the databse
//			}
//			
//			userRoles.add(singleUserRoles);
//		}
		
		
		session.getTransaction().commit();
		
		session.close();					
	}

	public List<User> getUsers()
	{
		//if(users == null)
		//if(users.isEmpty())
		//{
			//loadUsersFromDatabase();
			//System.out.println("new load");
		//}
		
		return users;
	}

//	public ArrayList<ArrayList<String>> getUserRoles()
//	{
//		return userRoles;
//	}
//	
//	 public void addNewUser()
//	 {
//		 System.out.println("add new user "+"------------------------");
//		 
//		 User u = new User();
//		 u.setFirstName("ffff");
//		 u.setLastName("lllllll");
//		 u.setUserName("uuuuuuuuuu");
//		 u.setPassword("pppppppppp");
//		 
//		
//		 
//		 ArrayList<String> singleUserRoles = new ArrayList<String>();
//		 singleUserRoles.add("ROLE_USER");
//		 
//		 //userRoles.add(singleUserRoles);
//		 
//		 System.out.println(singleUserRoles);
//		 
//		 List<Role> rList = new ArrayList<Role>();
//		 Role r = new Role();
//		 r.setUserRole("ROLE_USER");
//		 rList.add(r);
//		 u.setRoles(rList);
//		 users.add(u);
//	 }
//	 
	 public String deleteSelectedUser()
	 {
		 System.out.println(selectedUserId);
		 for (int i = 0; i < users.size(); i++)
		 { 
			 System.out.println(selectedUserId + " - "+users.get(i).getId());
			 if(selectedUserId == users.get(i).getId())
			 {
				 //users.remove(i);
				 
				 // remove from database and reload page
				 try
				 {
					 SessionFactory sessionFactory = GetSessionFactory.getInstance();
					 Session session = sessionFactory.openSession();//getCurrentSession();//openSession();
			
					 session.beginTransaction();
					 
					 session.delete(users.get(i));//OrUpdate(u);
					
					 session.getTransaction().commit();
					
					 session.close();
				
					 
					 // user deleted from the database
					 // Update the table
					 loadUsersFromDatabase();
					 
					 tableActiveTabManager.setActiveTab(TableActiveTabManager.USERS_TAB);
					 
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
//	        FacesMessage msg = new FacesMessage("User Selected", ((User) event.getObject()).getUserName());
//	        FacesContext.getCurrentInstance().addMessage(null, msg);
//	        
	        User selUser = ((User) event.getObject());
	        
	    	
	        selectedUserId = selUser.getId();
	        
	        for (int i = 0; i < users.size(); i++)
			{
				User u = users.get(i); 
				if(u.getId()==selUser.getId())
				{
					selectedUser = u;
					//System.out.println("User Selected: " + selectedUser.getName() + ", id: " + selectedUserId);
					//tableActiveTabManager.setActiveTab(TableActiveTabManager.USERS_TAB);
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

		public User getSelectedUser()
		{
//			SessionFactory sessionFactory = GetSessionFactory.getInstance();
//			Session session = sessionFactory.openSession();//getCurrentSession();//openSession();
//	
//			session.beginTransaction();
//			//com.entities.User userEntity = HibernateCommonMethods.getUserbyUsername(userName, session);// (com.entities.User)
//				
//		
//			User u = (User) session.get(User.class, selectedUserId);//OrUpdate(u);
//			
//			session.getTransaction().commit();
//			
//			session.close();
			
//			for (int i = 0; i < users.size(); i++)
//			{
//				if(users.get(i).getId()==selectedUserId)
//					return users.get(i);
//			}
//			
//			System.out.println("get user ----------------------");
//			return null;
			
			return selectedUser;
		}

		public void setSelectedUser(User selectedUser2)
		{
			selectedUser = selectedUser2;
//			for (int i = 0; i < users.size(); i++)
//			{
//				User u = users.get(i); 
//				if(u.getId()==selectedUser.getId())
//				{
//					users.set(i, selectedUser);
//					return;
//				}
//			}
			
			
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
//		     
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
