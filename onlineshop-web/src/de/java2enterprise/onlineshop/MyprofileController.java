package de.java2enterprise.onlineshop;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.MyprofileController;
import de.java2enterprise.onlineshop.ejb.MyProfileBeanLocal;
import de.java2enterprise.onlineshop.ejb.SigninBeanLocal;

@Named
@RequestScoped
public class MyprofileController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Customer customer;
	
	@EJB
	private MyProfileBeanLocal myProfileBeanLocal;
	
	@EJB private SigninBeanLocal signinBeanLocal;

	private String passwordRepetition;

	public Customer getCustomer() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ELContext elc = ctx.getELContext();
		ELResolver elr = ctx.getApplication().getELResolver();
		SigninController signinController = (SigninController) elr.getValue(elc, null, "signinController");
		this.customer = signinController.getCustomer();
		return customer;

	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getPasswordRepetition() {
		return passwordRepetition;
	}
	
	public void setPasswordRepetition(String passwordRepetition) {
		this.passwordRepetition = passwordRepetition;
	}
	
	public String changePassword() {
		return "changepassword";
	}
	
	public void persistNewPassword() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
		
		if (customer.getPassword().equals(passwordRepetition)) {
			try {
				
				customer = myProfileBeanLocal.changeProfileData(customer);
				FacesMessage m = new FacesMessage(bundle.getString("signin.passwordmodifiedsuccess"), "");
				context.addMessage("changepasswordForm", m);
			}
				catch (EJBException e) {
					FacesMessage fm = new FacesMessage(
							FacesMessage.SEVERITY_WARN,
							e.getMessage(),
							e.getCause().getMessage());
					context.addMessage("changepasswordForm", fm);
				}
	} else {
		FacesMessage m = new FacesMessage(bundle.getString("signin.passwordmissmatch"), bundle.getString("signing.tryagain"	));
		context.addMessage("changepasswordForm", m);
	}
	}
	
	public String deactivateAccount() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
		
		try {
			
			myProfileBeanLocal.deactivateAccount(getCustomer());
			HttpSession session = SessionUtils.getSession();
			session.invalidate();
			FacesMessage m = new FacesMessage(bundle.getString("signin.deactivated"), bundle.getString("bye"));
			context.addMessage("welcomesite", m);
			
		} catch (EJBException e) {
			FacesMessage fm = new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					e.getMessage(),
					e.getCause().getMessage());
			context.addMessage("myprofileForm", fm);
		}	
		
		return "/index.xhtml";
	}
	

	public String persist() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
		
			try {
				customer = myProfileBeanLocal.changeProfileData(customer);
				FacesMessage m = new FacesMessage(bundle.getString("signin.modified"), "");
				context.addMessage("myprofileForm", m);
			} catch (EJBException e) {
					FacesMessage fm = new FacesMessage(
							FacesMessage.SEVERITY_WARN,
							e.getMessage(),
							e.getCause().getMessage());
					context.addMessage("myprofileForm", fm);
				}	

		return "/myprofileForm.xhtml";
	}

}
