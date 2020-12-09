package de.java2enterprise.onlineshop.ejb;

import javax.ejb.Local;
import de.java2enterprise.onlineshop.model.Purchase;

@Local
public interface PurchaseBeanLocal {
	public Purchase persist(Purchase purchase);

}
