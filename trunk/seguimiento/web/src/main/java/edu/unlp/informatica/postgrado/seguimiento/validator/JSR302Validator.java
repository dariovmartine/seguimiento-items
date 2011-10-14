/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unlp.informatica.postgrado.seguimiento.validator;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 *
 * @author juliano
 */
class JSR302Validator<T> implements IValidator<T>, INullAcceptingValidator<T> {
    
    private static transient ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    
    private String propertyName;
    private Class<T> propertyClass;
    private final IModel currentValue;
    private Component parent;
    private Properties p = new Properties();

	private IModel<String> label; 

    public JSR302Validator(String propertyName, Class<T> propertyClass, IModel currentValue, Component parent, IModel<String> label) {
        this.propertyName = propertyName;
        this.propertyClass = propertyClass;
        this.currentValue = currentValue;
        this.label = label;
        this.parent = parent;
        try {
			p.load(this.getClass().getClassLoader()  
	                .getResourceAsStream("ValidationMessages.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    private String getString(String key)
	{
    	try {
    		return  parent.getString(key);	
		} catch (MissingResourceException e) {
			key = key.subSequence(1, key.length() -1).toString();
			if (p.containsKey(key)) {
				return (String) p.get(key);
			}
			throw new MissingResourceException("No encuentro",parent.getClass().getName(),key);
		}
	}

    public void validate(IValidatable iv) {
        // Only validates changed values
        if (currentValue != null && iv.getValue() != null && iv.getValue().equals(currentValue.getObject())) {
            return;
        }
        Validator validator = factory.getValidator();
        
        // El caso de los multiples choice
        Object value = iv.getValue(); 
        if (value instanceof List) {
        	List l = (List) value;
        	value = null;
        	if (l.size() == 1) {
        		value = l.get(0);
        	} 
        }
        
        Set<ConstraintViolation<T>> violations = validator.validateValue(propertyClass, propertyName, value);
        for (ConstraintViolation<T> v : violations) {
            ValidationError error = new ValidationError();
            String errorMessage;
            try {
                String key = v.getMessageTemplate();
                errorMessage = getString(v.getMessageTemplate());
                if (errorMessage != null) {
                    errorMessage = replaceVariables(errorMessage, v);
                }
            } catch (Exception e) {
                errorMessage = v.getMessageTemplate();
            }
            error.setMessage(errorMessage);
            iv.error(error);
        }
    }

    private String replaceVariables(String message, ConstraintViolation<T> violation) {
    	String name = parent.getString(propertyName, null, propertyName);
    	if (label != null) {
    		name = label.getObject();
    	}
        message = message.replaceAll("\\{fieldName\\}", name);
        Map<String, Object> attributes = violation.getConstraintDescriptor().getAttributes();
        for (String attrName : attributes.keySet()) {
            message = message.replaceAll("\\{" + attrName + "\\}", attributes.get(attrName).toString());
        }
        return message;
    }
    
}
