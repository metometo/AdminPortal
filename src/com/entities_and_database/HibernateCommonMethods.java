package com.entities_and_database;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class HibernateCommonMethods
{
	public static com.entities_and_database.User getUserbyUsername(String username, Session session)
	{
		SQLQuery query = session.createSQLQuery("select * from user s where s.userName = :usrname");
				query.addEntity(com.entities_and_database.User.class);
				query.setParameter("usrname", username);
				List<com.entities_and_database.User> users = query.list();
				
		if(users!=null)
			if(users.size()==1)
				return users.get(0);
			else if(users.size()>1)
			{
				//System.out.println(users.size()+"-----------"+users.get(1).getUserName());
				throw new AssertionError("Multiple users with same username - check the sing up restrictions");
			}
				
		
		return null;
	}
}
