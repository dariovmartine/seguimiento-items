package edu.unlp.informatica.postgrado.seguimiento.view;

import org.apache.wicket.IClusterable;

public class Item extends edu.unlp.informatica.postgrado.seguimiento.item.model.Item implements IClusterable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8881899385261084449L;
	
	public Item(edu.unlp.informatica.postgrado.seguimiento.item.model.Item item) {
		this.setId(item.getId());
		this.setName(item.getName());
		this.setState(item.getState());
	}

	

}
