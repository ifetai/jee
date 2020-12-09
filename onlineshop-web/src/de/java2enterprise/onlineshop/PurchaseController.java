package de.java2enterprise.onlineshop;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import de.java2enterprise.onlineshop.model.Purchase;
import de.java2enterprise.onlineshop.ejb.PurchaseBeanLocal;
import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Item;

@Named
@RequestScoped
public class PurchaseController implements Serializable {
	private static final long serialVersionUID = 1L;

	private final static Logger log = Logger.getLogger(PurchaseController.class.toString());

	@EJB
	private PurchaseBeanLocal purchaseBeanLocal;

	@Inject
	private Purchase purchase;

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public Customer getCustomer() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ELContext elc = ctx.getELContext();
		ELResolver elr = ctx.getApplication().getELResolver();
		SigninController signinController = (SigninController) elr.getValue(elc, null, "signinController");
		return signinController.getCustomer();
	}

	public String persist(Item item) {

		try {

			purchase.setItem(item);
			purchase.setQuantity(item.getQuantity());
			purchase.setCustomer(getCustomer());
			purchase.setSold(LocalDateTime.now());
			purchaseBeanLocal.persist(purchase);

			log.info(item + " bought by " + getCustomer());

		} catch (EJBException e) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("sellingListForm", fm);
			log.severe(e.getMessage());

		}

		return "/search.jsf";

	}

}
