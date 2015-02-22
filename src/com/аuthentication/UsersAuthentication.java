package com.àuthentication;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.entities.helpers.GetSessionFactory;
import com.entities.helpers.HibernateCommonMethods;
import com.entities.helpers.Role;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;
 
@Repository
public class UsersAuthentication implements UserDetailsService  {
 
//    @Autowired
//    private SessionFactory sessionFactory;
 
//    @Override
//    public void addEmployee(EmployeeEntity employee) {
//        this.sessionFactory.getCurrentSession().save(employee);
//    }
// 
//    @SuppressWarnings("unchecked")
//    @Override
//    public List<EmployeeEntity> getAllEmployees() {
//        return this.sessionFactory.getCurrentSession().createQuery("from Employee").list();
//    }
// 
//    @Override
//    public void deleteEmployee(Integer employeeId) {
//        EmployeeEntity employee = (EmployeeEntity) sessionFactory.getCurrentSession().load(
//                EmployeeEntity.class, employeeId);
//        if (null != employee) {
//            this.sessionFactory.getCurrentSession().delete(employee);
//        }
//    }
 
 
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException
    {
       // System.out.println("Getting access details from employee dao !!");
 
        // Ideally it should be fetched from database and populated instance of
        // #org.springframework.security.core.userdetails.User should be returned from this method
        
//        ArrayList<Role> userList = new ArrayList<Role>();
//        userList.add(new Role());
     //   user;
        
        SessionFactory sessionFactory = GetSessionFactory.getInstance();
        Session session = sessionFactory.openSession();//getCurrentSession();// closes session after commit or rolled back //openSession()-manual close
        
        
        
        
        session.beginTransaction();
        com.entities.User userEntity = HibernateCommonMethods.getUserbyUsername(username, session);//(com.entities.User) session.load(com.entities.User.class, 1);
        session.getTransaction().commit();
        
        ArrayList<Role> role = new ArrayList<Role>();
        Role r = new Role();
        r.setUserRole(userEntity.getRole());
        role.add(r);
        
        UserDetails user = null;
        if(userEntity!=null)
        	user = new User(userEntity.getName(), userEntity.getPassword(), role);
        //else
        	//user = new User(username, null, null);
                		
        //  new User(username, "password", true, true, true, true, new GrantedAuthority[]{ new GrantedAuthorityImpl("ROLE_USER") });
       	session.close();
        
       	return user;
    }
}