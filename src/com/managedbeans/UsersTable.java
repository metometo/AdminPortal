package com.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.entities.GetSessionFactory;
import com.entities.HibernateCommonMethods;
import com.entities.User;

@ManagedBean(name="usersData")
@SessionScoped
public class UsersTable	implements Serializable
{
	private List<User> users = new ArrayList<User>();
	private ArrayList<ArrayList<String>> userRoles = new ArrayList<ArrayList<String>>();
	
	public UsersTable()
	{
		// load users data from the databse
		loadUsersFromDatabase();
	}
	
	private void loadUsersFromDatabase()
	{
		
		SessionFactory sessionFactory = GetSessionFactory.getInstance();
		Session session = sessionFactory.openSession();//getCurrentSession();//openSession();

		session.beginTransaction();

		SQLQuery query = session.createSQLQuery("select * from user s");
		query.addEntity(com.entities.User.class);
		users = query.list();
		
		//System.out.println(users.get(0).getRoles());// call roles to pull them from the database
		for (int i = 0; i < users.size(); i++)
		{
			ArrayList<String> singleUserRoles = new ArrayList<String>();
			
			for (int j = 0; j < users.get(i).getRoles().size(); j++)
			{
				singleUserRoles.add(users.get(i).getRoles().get(j).getAuthority());
			}
			
			userRoles.add(singleUserRoles);
		}
		
		
		session.getTransaction().commit();
		
		session.close();					
		
	}

	public List<User> getUsers()
	{
		return users;
	}

	public ArrayList<ArrayList<String>> getUserRoles()
	{
		return userRoles;
	}
	
	
}
