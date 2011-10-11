package edu.unlp.informatica.postgrado.seguimiento.view.item;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class ItemEditPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3342982114735537862L;

	private ItemEditForm formInput;

	@SuppressWarnings("serial")
	public ItemEditPanel(String id) {
		
		super(id);
		// create a feedback panel
		final Component feedback = new FeedbackPanel("feedback").setOutputMarkupPlaceholderTag(true);
		add(feedback);
		
		add(formInput = new ItemEditForm());
		formInput.setEditMode(true);
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
				Item newVersion = (Item) getForm().getModelObject();
				
				Item i = DataSourceLocator.getInstance().getItemService()
				.getById(newVersion.getId());
		
				i.setName(newVersion.getName());
				i.setState(newVersion.getState());
				
				DataSourceLocator.getInstance().getItemService().udpate(i);
				
				((ModalWindow) getForm().getParent().getParent()).close(target);

				// Esto es para que se refresque la grilla de datos
				target.add(this.getForm().getParent().getParent().getParent());
			}

			@Override
			protected void onError(AjaxRequestTarget target) {
				target.add(feedback);
				
			}

			
		});
	}
	
	public void setItemId(Long itemId) {

		Item i = DataSourceLocator.getInstance().getItemService().getById(itemId);
				
		formInput.setModel(new CompoundPropertyModel<Item>(i));
	}
}
