package de.java2enterprise.onlineshop.ejb;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import de.java2enterprise.onlineshop.model.Purchase;

/**
 * Session Bean implementation class PurchaseBean
 */
@Stateless
public class PurchaseBean implements PurchaseBeanRemote, PurchaseBeanLocal {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Purchase persist(Purchase purchase) {
		try {
			em.persist(purchase);
			purchase.getItem().setStock(purchase.getItem().getStock() - purchase.getQuantity());
			em.merge(purchase.getItem());

		} catch (Exception e) {
			throw (EJBException) new EJBException(e).initCause(e);
		}

		return purchase;
	}

}
