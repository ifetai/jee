package de.java2enterprise.onlineshop.ejb;

import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Item;
import de.java2enterprise.onlineshop.model.Purchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class ShoppingCartBean
 */
@Stateful
public class ShoppingCartBean implements ShoppingCartBeanLocal, ShoppingCartBeanRemote {

	private List<Item> items = new ArrayList<>();
	
	private Item item;

	@PersistenceContext
	private EntityManager em;
	

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	/**
     * @see ShoppingCartBeanLocal#persist()
     */
    public BigDecimal persist(Long id) {
    	BigDecimal sum = new BigDecimal(0);
    	Customer customer = em.find(Customer.class, id);
		Iterator<Item> i = items.iterator();
		Item item;
		while (i.hasNext()) {
			Item it = i.next();
			try {
				item = em.find(Item.class, it.getId());
				Purchase purchase = new Purchase();
				purchase.setItem(item);
				purchase.setCustomer(customer);
				purchase.setSold(LocalDateTime.now());
				purchase.setQuantity(it.getQuantity());
				em.persist(purchase);
				sum = sum.add(item.getPrice());
				item.setStock(item.getStock() - purchase.getQuantity());
				em.merge(item);				
				i.remove();
				
			} catch (Exception e) {
				throw (EJBException) new EJBException(e).initCause(e);
			}

		}
		
		return sum;
    }
    
    @Override
	public List<Item> getItems() {
    	return items;
	}
    
	@Override
	public Boolean cartIsNotEmpty() {
		return !items.isEmpty();
	}

	/**
     * @see ShoppingCartBeanLocal#removeItem(Long)
     */
    public void removeItem(Long id) {
    	for (Item i : items){
			if (i.getId() == id) {
				i.setQuantity(0);
			}
		}
		items.removeIf(i -> i.getId() == id);
    }

	/**
     * @throws CloneNotSupportedException 
	 * @see ShoppingCartBeanLocal#addItem(Item)
     */
    public void addItem(Item item) throws CloneNotSupportedException {
    	if (!contains(item)) {
			items.add((Item) item.clone());
		}

		else {
			addQuantity(item);
		}
    }
    
	private boolean contains(Item item) {
		for (Item i : items) {
			if (i.getId() == item.getId()) {
				return true;
			}
		}
		return false;
	}
    
	private void addQuantity(Item item) {
		for (Item i : items) {
			if (i.getId() == item.getId()) {
				i.setQuantity(i.getQuantity() + item.getQuantity());
				;
			}
		}
	}	

}
