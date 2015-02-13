package com.àuthentication;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
 
@Repository
public class UsersAuthentication implements UserDetailsService  {
 
    @Autowired
    private SessionFactory sessionFactory;
 
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
        System.out.println("Getting access details from employee dao !!");
 
        // Ideally it should be fetched from database and populated instance of
        // #org.springframework.security.core.userdetails.User should be returned from this method
        ArrayList<Role> userList = new ArrayList<Role>();
        userList.add(new Role());
        
        UserDetails user = new User("user", "123", userList);
        		//  new User(username, "password", true, true, true, true, new GrantedAuthority[]{ new GrantedAuthorityImpl("ROLE_USER") });
        return user;
    }
}