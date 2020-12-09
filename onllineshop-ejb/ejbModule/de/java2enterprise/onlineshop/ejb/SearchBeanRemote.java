package de.java2enterprise.onlineshop.ejb;

import java.util.List;

import javax.ejb.Remote;

import de.java2enterprise.onlineshop.model.Item;

@Remote
public interface SearchBeanRemote {
	public List<Item> findAll();
}
