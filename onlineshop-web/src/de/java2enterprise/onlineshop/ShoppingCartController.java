package de.java2enterprise.onlineshop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Item;
import de.java2enterprise.onlineshop.ShoppingCartController;
import de.java2enterprise.onlineshop.ejb.ShoppingCartBeanLocal;

@Named
@SessionScoped
public class ShoppingCartController implements Serializable {
	private static final long serialVersionUID = 1L;

	private final static Logger log = Logger.getLogger(ShoppingCartController.class.toString());
	
	
	@EJB
	private ShoppingCartBeanLocal shoppingCartBeanLocal;
	

	public List<Item> getItems() {
		return shoppingCartBeanLocal.getItems();
	}

	public void addItem(Item item) throws CloneNotSupportedException {
		shoppingCartBeanLocal.addItem(item);
	}

	public Item getItem() {
		return shoppingCartBeanLocal.getItem();
	}

	public void setItem(Item item) {
		shoppingCartBeanLocal.setItem(item);
	}
	
	public Boolean cartIsNotEmpty()	{		
		return shoppingCartBeanLocal.cartIsNotEmpty();
	}
	
	public String showDetail(Item item) {
		shoppingCartBeanLocal.setItem(item);
		return "/shoppingcartdetail.jsf";
	}

	public Customer getCustomer() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ELContext elc = ctx.getELContext();
		ELResolver elr = ctx.getApplication().getELResolver();
		SigninController signinController = (SigninController) elr.getValue(elc, null, "signinController");
		return signinController.getCustomer();
	}

	public void removeItem(Long id) {
		shoppingCartBeanLocal.removeItem(id);
	}

	public String persist() {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
		
		Customer customer = getCustomer();
		try {
		BigDecimal sum;
		sum = shoppingCartBeanLocal.persist(customer.getId());
		FacesMessage m = new FacesMessage(bundle.getString("allitemspurchased"), bundle.getString("purchasedsum") + " " + sum);
		context.addMessage("shoppingcartform", m);
		
			} catch (EJBException e) {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getCause().getMessage());
				context.addMessage("shoppingcartform", fm);
				log.severe(e.getMessage());			

		}
		return "/shoppingcart.jsf";
	}

}
