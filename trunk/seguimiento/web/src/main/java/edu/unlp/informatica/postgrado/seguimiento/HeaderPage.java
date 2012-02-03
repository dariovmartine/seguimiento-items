/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.unlp.informatica.postgrado.seguimiento;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.html.panel.Panel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.security.Usuario;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

/**
 * Navigation panel for the examples project.
 * 
 * @author Eelco Hillenius
 */
public final class HeaderPage extends Panel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8762058885401839840L;

	/**
	 * Construct.
	 * 
	 * @param id
	 *            id of the component
	 * @param exampleTitleO
	 *            title of the example
	 * @param page
	 *            The example page
	 */
	public HeaderPage(String id, String exampleTitle, WebPage page)
	{
		super(id);
		
		add(new Label("exampleTitle", exampleTitle));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			add(new Label("usuario", "Usuario: " + authentication.getName()));
			add(new Label("nombre", "Nombre: " + getNombre(authentication.getName())));
		} else {
			add(new Label("usuario", ""));
			add(new Label("nombre", ""));
		}
		PopupSettings settings = new PopupSettings("sources", PopupSettings.RESIZABLE);
		settings.setWidth(800);
		settings.setHeight(600);
	}

	private String getNombre(String nombre) {
		try {
			Usuario usuario = DataSourceLocator.getInstance().getUsuarioService().findByUsername(nombre); 
			String nombrePersona = usuario.getPersona().getNombre();
			String roles = usuario.getRoles().toString();
			return nombrePersona + ", Roles: " + roles;
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "Error al recuperar usuario!";
	}
}
