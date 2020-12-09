package de.java2enterprise.onlineshop.ejb;

import java.util.List;

import javax.ejb.Local;

import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Purchase;

@Local
public interface HistoryBeanLocal {
	public List<Purchase> getAllSoldItems(Customer customer);
	
	public List<Purchase> getAllBoughtItems(Customer customer);
}
