package de.java2enterprise.onlineshop.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Purchase;

/**
 * Session Bean implementation class HistoryBean
 */
@Stateless
public class HistoryBean implements HistoryBeanRemote, HistoryBeanLocal {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Purchase> getAllBoughtItems(Customer customer) {
		List<Purchase> purchases = new ArrayList<>();
		try {
			TypedQuery<Purchase> query = em.createQuery(
					"SELECT p FROM Purchase p " 
					+ "WHERE p.customer= :customer",
					Purchase.class);
			query.setParameter("customer", customer);

			purchases = query.getResultList();
		} catch (Exception e) {
			throw (EJBException) new EJBException(e).initCause(e);
		}

		return purchases;
	}

	@Override
	public List<Purchase> getAllSoldItems(Customer customer) {
		List<Purchase> purchases = new ArrayList<>();
		try {
			TypedQuery<Purchase> query = em.createQuery(
					"SELECT p FROM Purchase p "
					+ "INNER JOIN p.item i " 
					+ "WHERE i.seller= :customer", Purchase.class);
			query.setParameter("customer", customer);

			purchases = query.getResultList();
		} catch (Exception e) {
			throw (EJBException) new EJBException(e).initCause(e);
		}

		return purchases;
	}

}
