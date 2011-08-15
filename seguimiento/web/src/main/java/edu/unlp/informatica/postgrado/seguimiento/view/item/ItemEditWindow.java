package edu.unlp.informatica.postgrado.seguimiento.view.item;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.panel.Panel;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class ItemEditWindow extends ModalWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3342982114735537862L;

	private ItemEditPanel itemEditPanel;

	private Item item;

	/**
	 * @param id
	 */
	public ItemEditWindow(String id) {
		super(id);
		itemEditPanel = new ItemEditPanel(getContentId());
		setContent(itemEditPanel);
		setTitle("Item");
		setCookieName("modal-2");

		setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

			private static final long serialVersionUID = 8779902619698219539L;

			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
				// setResult("Modal window 2 - close button");
				return true;
			}
		});

		setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3646057969858558792L;

			public void onClose(AjaxRequestTarget target) {
				// target.add(result);
			}
		});

		

	}

	public void setItemSel(Item itemSel) {

		Item i = DataSourceLocator.getInstance().getItemService()
				.getById(itemSel.getId());
		itemEditPanel.getFormInput().setName(i.getName());
		itemEditPanel
				.getFormInput()
				.getChoice()
				.setChoices(
						DataSourceLocator.getInstance().getEstadoService()
								.getNames());
		item = itemSel;
	}

	static class ItemEditPanel extends Panel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5214577882977352723L;

		private ItemEditForm formInput = new ItemEditForm();

		public ItemEditPanel(String id) {
			super(id);

			formInput.setEstados(DataSourceLocator.getInstance()
					.getEstadoService().getNames());
			add(getFormInput());

			formInput.add(new DateTimeField("dateTimeField") {

				/**
				 * 
				 */
				private static final long serialVersionUID = 8857477532890851349L;

				/**
				 * @see org.apache.wicket.extensions.yui.calendar.DateTimeField#configure(java.util.Map)
				 */
				@Override
				protected void configure(Map<String, Object> widgetProperties) {
					super.configure(widgetProperties);
					// IE 6 breaks layout with iframe - is that a YUI bug?
					widgetProperties.put("iframe", false);
				}
			});
			
			formInput.add(new AjaxLink<Void>("closeOK") {
				@Override
				public void onClick(AjaxRequestTarget target) {
					
					"".toString();
				}
			});

			formInput.add(new AjaxLink<Void>("closeCancel") {
				@Override
				public void onClick(AjaxRequestTarget target) {
					
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
}
