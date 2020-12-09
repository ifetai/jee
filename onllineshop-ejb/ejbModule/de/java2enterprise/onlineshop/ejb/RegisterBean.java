package de.java2enterprise.onlineshop.ejb;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.java2enterprise.onlineshop.model.Customer;

/**
* Session Bean
* Persists a new customer
*
* @author  ulrich.schmutz@students.ffhs.ch
* @version 1.0
*/
@Stateless
public class RegisterBean implements RegisterBeanRemote, RegisterBeanLocal {
   
	@PersistenceContext
	private EntityManager em;

	@Override
	public Customer persist(Customer customer) {
		
		 try {			
		customer.setActive();
		em.persist(customer);

		  } catch (Exception e){
			  throw (EJBException) new EJBException(e).initCause(e); 
		  }
		return customer;
	}

}
