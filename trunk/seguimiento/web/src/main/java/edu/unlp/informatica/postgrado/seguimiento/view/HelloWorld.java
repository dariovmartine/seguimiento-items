package edu.unlp.informatica.postgrado.seguimiento.view;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class HelloWorld extends WebPage {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1089949001128572279L;

	public HelloWorld() {
        add(new Label("message", "Hello World!"));
    }
}

