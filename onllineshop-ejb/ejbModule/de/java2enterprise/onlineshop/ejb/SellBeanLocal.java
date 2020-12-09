package de.java2enterprise.onlineshop.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.servlet.http.Part;

import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Item;

@Local
public interface SellBeanLocal {
	public Item persist(Item item, Part part);
	
	public List<Item> allMySellingItems(Item item, Customer customer);
	
	public byte[] getPhoto(long id);
	
	public void terminateSale(Item item);
	
	public Item persistModification(Item item, Part part);
}
