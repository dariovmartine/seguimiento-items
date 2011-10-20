package edu.unlp.informatica.postgrado.seguimiento.item;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.service.EstadoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ItemService;

@ContextConfiguration(locations={"classpath:item_spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestXMLConfiguration {

	@Autowired
	ItemService  myService;
	
	@Autowired
	EstadoService estadoService; 

	@Test
	public void test() {		
		
		Estado s = new Estado();
		s.setNombre("terminado");
				
		try {
			estadoService.save(s);
		
			Item i = new Item();
			i.setTitulo("juan");
			
			
			//myService.save(i);
			i = new Item();
			i.setTitulo("mariano");
			
			//myService.save(i);
			
			//assertTrue("Debería haberse grabado algo.", myService.find().size() > 0);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
	}
}
