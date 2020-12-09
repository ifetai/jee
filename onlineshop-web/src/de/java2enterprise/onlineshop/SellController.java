package de.java2enterprise.onlineshop;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import de.java2enterprise.onlineshop.ejb.SellBeanLocal;
import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Item;

@Named
@SessionScoped
public class SellController implements Serializable {
	private static final long serialVersionUID = 1L;

	private final static Logger log = Logger.getLogger(SellController.class.toString());	

	private Part part;

	@Inject
	private Item item;
	
	@EJB
	private SellBeanLocal sellBeanLocal;

	private List<Item> Items;

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
		this.Items = getSellingItems();
		return Items;
	}

	public void setItems(List<Item> items) {
		Items = items;
	}

	public Customer getCustomer() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ELContext elc = ctx.getELContext();
		ELResolver elr = ctx.getApplication().getELResolver();
		SigninController signinController = (SigninController) elr.getValue(elc, null, "signinController");
		return signinController.getCustomer();
	}
	
	public String modifyItem(Item item) {
		this.item  = item;
		return "/modifyitem.xhtlm";
	}
	
	public String sellItem() {
		this.item = new Item();
		return "/sell.xhtlm";
	}

	public String persist() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
		
		try {
			
			item.setSeller(getCustomer());		
			item = sellBeanLocal.persist(item, part);
			
			FacesMessage m = new FacesMessage(bundle.getString("item.save"), bundle.getString("item.offer") + " " + item.getTitle());

			context.addMessage("sellingListForm", m);
			
		} catch (EJBException e) {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						e.getMessage(),
						e.getCause().getMessage());
				context.addMessage("sellingListForm", fm);	
				log.severe(e.getMessage());
			
		} 
		return "/sellinglist.xhtlm";
	}
	
	public String persistModification() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
		
		try {	item = sellBeanLocal.persistModification(item, part);
		
		FacesMessage m = new FacesMessage(bundle.getString("item.modifysuccess"), bundle.getString("item.modified") + " " + item.getTitle());

		context.addMessage("sellingListForm", m);
		
	} catch (EJBException e) {
			FacesMessage fm = new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					e.getMessage(),
					e.getCause().getMessage());
			context.addMessage("sellForm", fm);	
			log.severe(e.getMessage());
		
	} 
			
		return "/sellinglist.xhtlm";
	}
	

	
	public String endSale(Item item) {
		try {
			sellBeanLocal.terminateSale(item);
			
		
		} catch (EJBException e) {
			FacesMessage fm = new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					e.getMessage(),
					e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("sellingListForm", fm);
			log.severe(e.getMessage());
		}
		return "/sellinglist.xhtlm";
	}

	public List<Item> getSellingItems() {
		try {
			
			System.out.println(getCustomer());
			return sellBeanLocal.allMySellingItems(item, getCustomer());	

		}  catch (EJBException e) {
			FacesMessage fm = new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					e.getMessage(),
					e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("sellingListForm", fm);		
			log.severe(e.getMessage());
		
	} 
		
		return new ArrayList<Item>();

	}
	

}
