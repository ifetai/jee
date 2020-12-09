package de.java2enterprise.onlineshop.ejb;

import javax.ejb.Remote;

import de.java2enterprise.onlineshop.model.Purchase;

@Remote
public interface PurchaseBeanRemote {
	public Purchase persist(Purchase purchase);
}
