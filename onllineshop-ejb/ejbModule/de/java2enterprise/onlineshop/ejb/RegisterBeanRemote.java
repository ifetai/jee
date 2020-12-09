package de.java2enterprise.onlineshop.ejb;

import javax.ejb.Remote;

import de.java2enterprise.onlineshop.model.Customer;

@Remote
public interface RegisterBeanRemote {
	public Customer persist(Customer customer);
}
