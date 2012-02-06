package edu.unlp.informatica.postgrado.seguimiento.view.estado;

import java.util.Arrays;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoEstado;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;

public class EstadoEditForm extends BaseEntityForm<Estado> {

	private static final long serialVersionUID = 7499289901011022854L;	

	private FormComponent<String> textField = null;
		
	public EstadoEditForm() {
		
		super("inputForm", new Estado());

		textField = new TextField<String>("nombre").setLabel(new Model<String>("Nombre"));
		add(textField);
		
//		ListChoice<TipoEstado> tiposDeEstados;
//		add(tiposDeEstados = new ListChoice<TipoEstado>("tipoEstado"));
//		tiposDeEstados.setChoices(Arrays.asList(TipoEstado.values()));
//		tiposDeEstados.setLabel(new Model<String>("TiposEstado"));
		
		RadioChoice<TipoEstado> rc = new RadioChoice<TipoEstado>("tipoEstado", Arrays.asList(TipoEstado.values())).setSuffix("</br>");
		rc.setLabel(new Model<String>("number"));
		rc.setRequired(true);
		add(rc);

	}
	
	
}
