package de.java2enterprise.onlineshop.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.java2enterprise.onlineshop.model.Customer;

/**
* Session bean
* verifies the signin credentials in the database
*
* @author  ulrich.schmutz@students.ffhs.ch
* @version 1.0
*/
@Stateless
public class SigninBean implements SigninBeanRemote, SigninBeanLocal {
    
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Customer> find(String email, String password) {
		List<Customer> customers = new ArrayList<>();
		  try {	
	
		TypedQuery<Customer> query = em.createQuery(
				"SELECT c FROM Customer c "
				+ "WHERE c.email= :email "
				+ "AND c.password= :password "
				+ "AND c.active= 1", Customer.class);
		query.setParameter("email", email);
		query.setParameter("password", password);
		customers =  query.getResultList();
		  } catch (Exception e){
			  throw (EJBException) new EJBException(e).initCause(e); 
		  }
		  return customers;
		
	}



}
