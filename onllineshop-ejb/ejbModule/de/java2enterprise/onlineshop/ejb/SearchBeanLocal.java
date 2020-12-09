package de.java2enterprise.onlineshop.ejb;

import java.util.List;

import javax.ejb.Local;

import de.java2enterprise.onlineshop.model.Item;

@Local
public interface SearchBeanLocal {
	public List<Item> findAll();
}
