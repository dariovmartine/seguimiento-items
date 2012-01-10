package edu.unlp.informatica.postgrado.seguimiento.item;

public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8904238440273874317L;

	public ServiceException(Exception e) {
		super(e);
	}
	
	public ServiceException(String s) {
		super(s);
	}

}
