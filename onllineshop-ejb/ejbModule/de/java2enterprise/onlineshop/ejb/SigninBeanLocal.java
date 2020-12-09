package de.java2enterprise.onlineshop.ejb;

import java.util.List;

import javax.ejb.Local;

import de.java2enterprise.onlineshop.model.Customer;

@Local
public interface SigninBeanLocal {
	public List<Customer> find(String email, String password);

}
