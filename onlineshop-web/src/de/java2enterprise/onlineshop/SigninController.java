package de.java2enterprise.onlineshop;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import de.java2enterprise.onlineshop.ejb.SigninBeanLocal;
import de.java2enterprise.onlineshop.model.Customer;

/**
* Controls the signin process
*
* @author  ulrich.schmutz@students.ffhs.ch
* @version 1.0
*/

@Named
@SessionScoped
public class SigninController implements Serializable {
	private static final long serialVersionUID = 1L;

	public SigninController() {
		setLoggedIn(false);
	}

	@PersistenceContext
	private EntityManager em;

	private String email;

	private String password;

	private boolean loggedIn;

	@Inject
	private Customer customer;

	@EJB
	private SigninBeanLocal signinBeanLocal;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String signout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "/index.xhtml";
	}

	public String find() {

		List<Customer> customers = signinBeanLocal.find(getEmail(), getPassword());
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());

		try {
			if (customers.isEmpty()) {
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, bundle.getString("singin.fail"),
						bundle.getString("signing.tryagain"));
				context.addMessage("signinForm", m);

			} else {
				customer = customers.get(0);
				// set customer into session properties
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("customer", customer);

				loggedIn = true;

				FacesMessage m = new FacesMessage(bundle.getString("signin.success"),bundle.getString("signin.welcome")
						+ customers.get(0).getFirstname() + " " + customers.get(0).getName());
				context.addMessage("signinForm", m);
			}
		} catch (EJBException e) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getCause().getMessage());
			context.addMessage("signinform", fm);
		}

		return "/signin.xhtml";

	}

}
