package de.java2enterprise.onlineshop.ejb;

import javax.ejb.Local;

import de.java2enterprise.onlineshop.model.Customer;

@Local
public interface MyProfileBeanLocal {
	
	public void deactivateAccount(Customer customer);
	
	public Customer changeProfileData(Customer customer);
}
