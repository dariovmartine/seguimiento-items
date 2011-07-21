package edu.unlp.informatica.postgrado.seguimiento.view.item;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;

public class ItemEditForm extends Form<edu.unlp.informatica.postgrado.seguimiento.item.model.Item> {

	private static final long serialVersionUID = 7499289901011022854L;	

	private FormComponent<String> textField = null;
	
	private ListMultipleChoice<String> choice;
	
	private List<String> estados;
	
	private String name;	
	
	public ItemEditForm() {
		
		super("inputForm" );// , new CompoundPropertyModel<FormInputModel>(new FormInputModel()));

		textField = new TextField<String>("firstname").setRequired(true).setLabel(
				new Model<String>("String"));
		textField.setModel(new Model<String>());
		add(textField);
		
		add(choice = new ListMultipleChoice<String>("estadoSelection"));			
		
		add(new AjaxButton("saveButton", this)
		{
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				setName(textField.getModel().getObject());
				
				// repaint the feedback panel so that it is hidden
				//target.add("");
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				// repaint the feedback panel so errors are shown
				//target.add("");
				"".toString();
			}
		});

		add(new Button("resetButton")
		{
			@Override
			public void onSubmit()
			{
				// just set a new instance of the page
				//setResponsePage(SortingPane.class);
				"".toString();
			}
		}.setDefaultFormProcessing(false));
	}

	/**
	 * @return the textField
	 */
	public FormComponent<String> getTextField() {
		return textField;
	}

	/**
	 * @param textField the textField to set
	 */
	public void setTextField(FormComponent<String> textField) {
		this.textField = textField;
	}

	

	/**
	 * @return the estados
	 */
	public List<String> getEstados() {
		return estados;
	}

	/**
	 * @param estados the estados to set
	 */
	public void setEstados(List<String> estados) {
		this.estados = estados;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
		this.textField.getModel().setObject(name);
	}

	/**
	 * @return the choice
	 */
	public ListMultipleChoice<String> getChoice() {
		return choice;
	}

	/**
	 * @param choice the choice to set
	 */
	public void setChoice(ListMultipleChoice<String> choice) {
		this.choice = choice;
	}
	
	




	

}
