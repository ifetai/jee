package de.java2enterprise.onlineshop;

import java.util.regex.Pattern;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

@Named
@RequestScoped
public class ValidatorController {
	
	public void isEmail(FacesContext fc, UIComponent uic, Object obj) throws ValidatorException{
		String value = (String) obj;
		if (!Pattern.matches(
				"^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z0-9-.]+$", 
				value)){
					throw new ValidatorException(new FacesMessage("Falsche Emailadresse"));
				}
	}


}
