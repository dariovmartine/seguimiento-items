package edu.unlp.informatica.postgrado.seguimiento.configuracionitem;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;

@RunWith(JUnit4.class)
public class ConfiguracionItemTest {

  	@Test
	public void canChange() {
  		
  		ConfiguracionItem c = new ConfiguracionItem();
  		ConfiguracionEstado e = new ConfiguracionEstado();
  		
  		Estado actual = new Estado();
  		actual.setNombre("actual");
  		Estado nuevo = new Estado();
  		nuevo.setNombre("nuevo");
  		
  		e.setEstado(actual);
  		e.getProximosEstados().add(nuevo);
  		c.getProximosEstados().put(actual, e);
  		
  		assertTrue("Tendría que poder pasar de un estado al otro ", c.canChangeState(actual, nuevo));
  	}
  	
  	@Test
	public void cantChange() {
  		
  		ConfiguracionItem c = new ConfiguracionItem();
  		ConfiguracionEstado e = new ConfiguracionEstado();
  		
  		Estado actual = new Estado();
  		actual.setNombre("actual");
  		Estado nuevo = new Estado();
  		nuevo.setNombre("nuevo");
  		Estado invalido = new Estado();
  		invalido.setNombre("nuevo2");
  		
  		e.setEstado(actual);
  		e.getProximosEstados().add(nuevo);
  		c.getProximosEstados().put(actual, e);
  		
  		assertFalse("NO tendría que poder pasar de un estado al otro ", c.canChangeState(actual, invalido));
 	}
  	
  	@Test
	public void cantChangeBecauseNull() {
  		
  		ConfiguracionItem c = new ConfiguracionItem();
  		  		
  		Estado estado = new Estado();
  		estado.setNombre("nuevo");
  		  		 		
  		assertFalse("NO tendría que poder pasar de un estado al otro porque está en null", c.canChangeState(estado, estado));
 	}
}
