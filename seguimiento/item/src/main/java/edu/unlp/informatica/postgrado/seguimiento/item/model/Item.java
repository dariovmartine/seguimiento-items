package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import edu.unlp.informatica.postgrado.item.validators.ValidUserName;

// @ManagedBean    Lo mandamos al faces-config.xml
// @RequestScoped   Lo mandamos al faces-config.xml
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

	@ValidUserName
	@Size(min = 3, max = 12, message = "Must be between 3 and 12 chars")
	private String name;

	private String state;

	public String getState() {
		return state;
	}

	
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

}
