package edu.unlp.informatica.postgrado.seguimiento.view.listado;

import org.apache.wicket.model.LoadableDetachableModel;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;

public class ItemtDetachableModel extends LoadableDetachableModel<Item> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2628005817209019922L;
	
	private edu.unlp.informatica.postgrado.seguimiento.view.Item item;

	public ItemtDetachableModel(Item item) {
		this.item = new edu.unlp.informatica.postgrado.seguimiento.view.Item(item);
	}

	@Override
	protected edu.unlp.informatica.postgrado.seguimiento.view.Item load() {
		// TODO Auto-generated method stub
		return item;
	}

	
}
