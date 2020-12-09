package de.java2enterprise.onlineshop.ejb;

import javax.ejb.Asynchronous;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.java2enterprise.onlineshop.model.Customer;

/**
 * Session Bean implementation class MyProfileBean
 */
@Stateless
public class MyProfileBean implements MyProfileBeanRemote, MyProfileBeanLocal {
   
	@PersistenceContext
	private EntityManager em;

	@Override
	public Customer changeProfileData(Customer customer) {
		
		try {
		em.merge(customer);
		}		  catch (Exception e){
			  throw (EJBException) new EJBException(e).initCause(e); 
		  }
		return customer;
	}

	@Override
	@Asynchronous
	public void deactivateAccount(Customer customer) {
		customer.deactivate();
		try {
		em.merge(customer);
	}	 catch (Exception e){
		  throw (EJBException) new EJBException(e).initCause(e); 
	  }	
	}
	

}
