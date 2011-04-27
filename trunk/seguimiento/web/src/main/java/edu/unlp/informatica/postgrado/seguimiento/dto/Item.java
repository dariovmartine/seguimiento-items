package edu.unlp.informatica.postgrado.seguimiento.dto;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.Size;

import edu.unlp.informatica.postgrado.item.validators.ValidUserName;

@ManagedBean
@RequestScoped
public class Item {

	public List<String> autocomplete(String prefix) {

		ArrayList<String> result = new ArrayList<String>();
		result.add("England");
		result.add("France");
		result.add("Germany");
		result.add("Italy");
		result.add("Spain");
		result.add("Argentina");
		return result;
	}

	//@Size(min = 3, max = 12, message = "Must be between 3 and 12 chars")
	@ValidUserName
	private String name;

	private String state;

	public String getState() {
		return state;
	}

	
	public String getName() {
		return name;
	}

}
