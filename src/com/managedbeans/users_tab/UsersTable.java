package com.managedbeans.users_tab;

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
import com.entities.User;
import com.entities.helpers.GetSessionFactory;
import com.managedbeans.TableActiveTabManager;

@ManagedBean(name="usersData")
@SessionScoped
public class UsersTable	implements Serializable//, SelectableDataModel<User>
{
	private User selectedUser = new User();
	private int selectedUserId;
	private List<User> users = new ArrayList<User>();
		
	@ManagedProperty(value = "#{tableActiveTabManager}")
	TableActiveTabManager tableActiveTabManager;

	
	public UsersTable()
	{
		// load users data from the databse
		loadUsersFromDatabase();
	}
	
	
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
		
		session.getTransaction().commit();
		
		session.close();					
	}

	public List<User> getUsers()
	{
		return users;
	}

	
	public String deleteSelectedUser()
	{
		for (int i = 0; i < users.size(); i++)
		{ 
			 if(selectedUserId == users.get(i).getId())
			 {
				 
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
				 
			 }
		 }
		 
		 return "deleting_error";
	 }
	 
	 
	public void onRowSelect(SelectEvent event) 
	{        
        User selUser = ((User) event.getObject());
        
        selectedUserId = selUser.getId();
        
        for (int i = 0; i < users.size(); i++)
		{
			User u = users.get(i); 
			if(u.getId()==selUser.getId())
			{
				selectedUser = u;
				
				tableActiveTabManager.setActiveTab(TableActiveTabManager.USERS_TAB);
				
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
	 

	public User getSelectedUser()
	{
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser2)
	{
		selectedUser = selectedUser2;		
	}
}
