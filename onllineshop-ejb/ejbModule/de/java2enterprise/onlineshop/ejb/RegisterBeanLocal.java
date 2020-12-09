package de.java2enterprise.onlineshop.ejb;

import javax.ejb.Local;

import de.java2enterprise.onlineshop.model.Customer;

@Local
public interface RegisterBeanLocal {
	public Customer persist(Customer customer);
}
