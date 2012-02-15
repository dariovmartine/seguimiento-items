package edu.unlp.informatica.postgrado.seguimiento.prioridad;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import edu.unlp.informatica.postgrado.seguimiento.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Prioridad;
import edu.unlp.informatica.postgrado.seguimiento.item.service.PrioridadService;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.PrioridadRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={

	AppConfig.class, 
	PrioridadService.class,
	PrioridadRepository.class})
@Transactional
public class TestPrioridad {

	@Autowired
	PrioridadService myService;

	@Test
	public void test() {
		
		try {
			Prioridad p = new Prioridad();
			p.setNombre("prueba");
			
			myService.save(p);
			
			assertTrue("Debería haberse grabado una prioridad.", myService.find().size() == 1);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
	}
}
