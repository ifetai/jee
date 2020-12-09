package de.java2enterprise.onlineshop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import de.java2enterprise.onlineshop.ejb.SearchBeanLocal;
import de.java2enterprise.onlineshop.model.Item;

@Named
@SessionScoped
public class SearchController implements Serializable {
	 private static final long serialVersionUID = 1L;

	    private final static Logger log = Logger
	            .getLogger(SearchController.class.toString());

	    
	    @EJB
	    private SearchBeanLocal searchBeanLocal;

	    private List<Item> items;

	    public List<Item> getItems() {
	        items = findAll();
	        return items;
	    }

	    public void setItems(List<Item> items) {
	        this.items = items;
	    }

	    public List<Item> findAll() {
	        try {
	        	
	        	return searchBeanLocal.findAll();
	        } catch (EJBException e) {
				log.severe(e.getMessage());

	        }
	        return new ArrayList<Item>();
	    }

}
