package edu.unlp.informatica.postgrado.seguimiento.item.model.security;


public enum Rol {

	ROLE_SUPERVISOR("ROLE_SUPERVISOR"), 
	ROLE_USER("ROLE_USER"), 
	ROLE_TELLER("ROLE_TELLER");
	
	String value;
	Rol(String value) {
		this.value= value;
	}
	
	@Override public String toString() {
		return value;
	}
}
