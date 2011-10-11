/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unlp.informatica.postgrado.seguimiento.validator;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

/**
 *
 * This is a base default form for JPA entities.
 * It supports bean validation using the JSR 303 API.
 * @author juliano
 */
public class BaseEntityForm<T> extends Form<T> {

    private boolean validatorsAdded = false;
    
    private Class entityClass;
    private T entity;

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
        setModel(new CompoundPropertyModel(entity));
        this.entityClass = entity.getClass();
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    private boolean editMode;

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public BaseEntityForm(String id, T entity) {

        super(id);
        setEntity(entity);
    }

    @Override
    protected void onBeforeRender() {
        
    	super.onBeforeRender();
    	
    	if (!validatorsAdded) {
            final BeanInfo entityBeanInfo;

            try {
                entityBeanInfo = Introspector.getBeanInfo(entityClass);
            } catch (IntrospectionException ex) {
                throw new RuntimeException(ex);
            }
            final HashMap<String, PropertyDescriptor> entityProperties = new HashMap<String, PropertyDescriptor>();

            for (PropertyDescriptor p : entityBeanInfo.getPropertyDescriptors()) {
                entityProperties.put(p.getName(), p);
            }

            visitChildren(new IVisitor<Component, Void>() {

            	public void component(Component t, IVisit<Void> visit) {

                    if (t instanceof FormComponent) {
                        FormComponent c = (FormComponent) t;

                        if (entityProperties.containsKey(t.getId())) {
                            c.add(new JSR302Validator<T>(t.getId(), entityClass,editMode?c.getModel():null,BaseEntityForm.this));
                        }


                        c.add(new AjaxFormValidatingBehavior(((FormComponent) t).getForm(),"onsubmit"));

                    }
                }				
            });

            validatorsAdded = true;
        }
        
    }
}
