package de.java2enterprise.onlineshop.ejb;

import java.util.List;

import javax.ejb.Remote;
import javax.servlet.http.Part;

import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Item;

@Remote
public interface SellBeanRemote {
	public Item persist(Item item, Part part);
	
	public List<Item> allMySellingItems(Item item, Customer customer);
	
	public byte[] getPhoto(long id);	
	
	public void terminateSale(Item item);
	
	public Item persistModification(Item item, Part part);
	
}
