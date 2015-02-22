package com.entities.helpers;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class HibernateCommonMethods
{
	public static com.entities.User getUserbyUsername(String username, Session session)
	{
		SQLQuery query = session.createSQLQuery("select * from user s where s.userName = :usrname");
		query.addEntity(com.entities.User.class);
		query.setParameter("usrname", username);
		
		List<com.entities.User> users = query.list();
				
		if(users!=null)		// if there are some users wth this username (must be only one)
			if(users.size()==1)
				return users.get(0);
			else if(users.size()>1)
			{
				//throw new AssertionError("Multiple users with same username - check the sing up restrictions");
			}
				
		
		return null;
	}
}
