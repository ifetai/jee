package de.java2enterprise.onlineshop.ejb;

import java.util.List;

import javax.ejb.Remote;

import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Purchase;

@Remote
public interface HistoryBeanRemote {
	public List<Purchase> getAllSoldItems(Customer customer);
	
	public List<Purchase> getAllBoughtItems(Customer customer);

}
