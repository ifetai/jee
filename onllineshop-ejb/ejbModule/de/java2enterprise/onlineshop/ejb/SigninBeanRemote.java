package de.java2enterprise.onlineshop.ejb;

import java.util.List;

import javax.ejb.Remote;

import de.java2enterprise.onlineshop.model.Customer;

@Remote
public interface SigninBeanRemote {
	public List<Customer> find(String email, String password);
}
