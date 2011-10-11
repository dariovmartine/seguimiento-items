package edu.unlp.informatica.postgrado.seguimiento.view.item;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.Panel;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class ItemEditPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3342982114735537862L;

	private Item item;

	private ItemEditForm formInput = new ItemEditForm();

	public void setItemSel(Item itemSel) {

		Item i = DataSourceLocator.getInstance().getItemService()
				.getById(itemSel.getId());
		getFormInput().setName(i.getName());
		this.getFormInput().getChoice().setChoices(
			DataSourceLocator.getInstance().getEstadoService()
								.getNames());
		this.getFormInput().setItemId(i.getId());
		item = itemSel;
	}

	public Item getItem() {
		return item;
	}

	@SuppressWarnings("serial")
	public ItemEditPanel(String id) {
		
		super(id);

		formInput.setEstados(DataSourceLocator.getInstance().getEstadoService()
				.getNames());
		
		add(getFormInput());		

		formInput.add(new AjaxLink<Void>("closeCancel") {
			
			@Override
			public void onClick(AjaxRequestTarget target) {

				((ModalWindow) this.getParent().getParent().getParent())
						.close(target);
			}
		});
		
		
		formInput.add(new AjaxFormSubmitBehavior(formInput, "onsubmit")
		{
			@Override
			protected IAjaxCallDecorator getAjaxCallDecorator()
			{
				return new AjaxCallDecorator()
				{
					public CharSequence decorateScript(Component c, CharSequence script)
					{
						return script + "return false;";
					}
				};
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				
				Item i = DataSourceLocator.getInstance().getItemService()
				.getById(getItem().getId());
		
				i.setName(((ItemEditForm)getForm()).getTextField().getModelObject());
				//TODO:  i.setState(getFormInput().getState());
				DataSourceLocator.getInstance().getItemService().udpate(i);
				
				((ModalWindow) getForm().getParent().getParent())
						.close(target);
				
				target.add(this.getForm().getParent().getParent().getParent());
			}

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				"".toString();
			}
		});
	}

	/**
	 * @return the formInput
	 */
	public ItemEditForm getFormInput() {
		return formInput;
	}

	/**
	 * @param formInput
	 *            the formInput to set
	 */
	public void setFormInput(ItemEditForm formInput) {
		this.formInput = formInput;
	}
}
