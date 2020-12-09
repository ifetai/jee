package de.java2enterprise.onlineshop;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.UserTransaction;
import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.RegisterController;
import de.java2enterprise.onlineshop.ejb.RegisterBeanLocal;

/**
* Controls the register process
*
* @author  ulrich.schmutz@students.ffhs.ch
* @version 1.0
*/

@Named
@RequestScoped
public class RegisterController implements Serializable {

	private static final long serialVersionUID = 1L;	

	@Resource
	private UserTransaction ut;
		
	@Inject
	private Customer customer;
	
	@EJB
	private RegisterBeanLocal registerBeanLocal;
	
	public Customer getCustomer() {
		return customer;
	}
	
	public String setCustomer(Customer customer) {
		this.customer = customer;
		return "/search.xml";
	}
	
	public String persist() {
	
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
	
		try {
		
		
		customer = registerBeanLocal.persist(customer);
		FacesMessage m = new FacesMessage(bundle.getString("register.success"), "UserId: " + getCustomer().getId());
		context.addMessage("registerForm", m);
		}
		catch (EJBException e) {
			FacesMessage fm = new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					e.getMessage(),
					e.getCause().getMessage());
			context.addMessage("registerForm", fm);
		}		

		return "/register.xhtml";
	}

}
