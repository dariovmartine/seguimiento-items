/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unlp.informatica.postgrado.seguimiento.validator;

import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.wicket.Component;
import org.apache.wicket.Localizer;
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

    public JSR302Validator(String propertyName, Class<T> propertyClass, IModel currentValue, Component parent) {
        this.propertyName = propertyName;
        this.propertyClass = propertyClass;
        this.currentValue = currentValue;
        this.parent=parent;
    }

    public void validate(IValidatable iv) {
        // Only validates changed values
        if (currentValue != null && iv.getValue() != null && iv.getValue().equals(currentValue.getObject())) {
            return;
        }
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validateValue(propertyClass, propertyName, iv.getValue());
        for (ConstraintViolation<T> v : violations) {
            ValidationError error = new ValidationError();
            String errorMessage;
            try {
                String key = v.getMessageTemplate();
                errorMessage = parent.getString(v.getMessageTemplate());
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
        message = message.replaceAll("\\{fieldName\\}", parent.getString(propertyName, null, propertyName));
        Map<String, Object> attributes = violation.getConstraintDescriptor().getAttributes();
        for (String attrName : attributes.keySet()) {
            message = message.replaceAll("\\{" + attrName + "\\}", attributes.get(attrName).toString());
        }
        return message;
    }
    
}
