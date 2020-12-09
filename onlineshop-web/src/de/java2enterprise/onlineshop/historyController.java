package de.java2enterprise.onlineshop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import de.java2enterprise.onlineshop.ejb.HistoryBeanLocal;
import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Item;
import de.java2enterprise.onlineshop.model.Purchase;

@Named
@SessionScoped
public class historyController implements Serializable {
	private static final long serialVersionUID = 1L;

	private final static Logger log = Logger.getLogger(historyController.class.toString());

	private Part part;

	@Inject
	private Item item;
	
	@Inject
	private Purchase purchase;
	
	@EJB
	private HistoryBeanLocal historyBeanLocal;
	
	private List<Purchase> purchases;
	
	private List<Purchase> sales;

	private List<Item> items;

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}


	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public List<Purchase> getPurchases() {
		this.purchases = getAllBoughtItems();
		return purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}
		
	public Customer getCustomer() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ELContext elc = ctx.getELContext();
		ELResolver elr = ctx.getApplication().getELResolver();
		SigninController signinController = (SigninController) elr.getValue(elc, null, "signinController");
		return signinController.getCustomer();
	}
	
	public List<Purchase> getSales() {
		this.sales = getAllSoldItems();
		return sales;
	}

	public void setSales(List<Purchase> sales) {
		this.sales = sales;
	}

	public List<Purchase> getAllBoughtItems() {
	
			try {
				return historyBeanLocal.getAllBoughtItems(getCustomer());

			} catch (EJBException e) {
				log.severe(e.getMessage());
			}
			return new ArrayList<Purchase>();	

	}
	
	public List<Purchase> getAllSoldItems(){
		try {
			return historyBeanLocal.getAllSoldItems(getCustomer());

		} catch (EJBException e) {
			log.severe(e.getMessage());
		}
		return new ArrayList<Purchase>();


}
}
