package com.entities.helpers;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
* Get one EntityManager for the whole application.
* This class is Singleton  
*/
public class GetSessionFactory
{
	private static SessionFactory sessionFactory;
	
	private GetSessionFactory() {}
	
	/**
	 * Through this method, the entire application gets the EntityManager Singleton
	 * @return EntityManager singleton for the whole application.
	 */
	public static SessionFactory getInstance()
	{
		if(sessionFactory == null)
		{
			 Configuration configuration = new Configuration().configure();
		        
		     ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
		                configuration.getProperties()).build();
		        
		     sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		       
		}			    
		
		return sessionFactory;
	}
}
