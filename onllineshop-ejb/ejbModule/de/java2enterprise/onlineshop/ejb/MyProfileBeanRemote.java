package de.java2enterprise.onlineshop.ejb;

import javax.ejb.Remote;

import de.java2enterprise.onlineshop.model.Customer;

@Remote
public interface MyProfileBeanRemote {
	
	public void deactivateAccount(Customer customer);
	
	public Customer changeProfileData(Customer customer);
}
