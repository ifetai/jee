package de.java2enterprise.onlineshop.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.java2enterprise.onlineshop.model.Item;

/**
 * Session Bean implementation class SearchBean
 */
@Stateless
public class SearchBean implements SearchBeanRemote, SearchBeanLocal {
    
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Item> findAll() {
		List<Item> items = new ArrayList<>();
		try {
		
	       	TypedQuery<Item> query = em.createQuery(
	       				"SELECT i FROM Item i " 
						+ "WHERE i.stock > 0 ", Item.class);	
	            items = query.getResultList();
		} catch (Exception e) {
			throw (EJBException) new EJBException(e).initCause(e);
		}
		
		return items;
	}

}
