package edu.unlp.informatica.postgrado.seguimiento.persona;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import edu.unlp.informatica.postgrado.seguimiento.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.mapper.DefaultDozerBeanMapper;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.service.PersonaService;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.PersonaRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={
	DefaultDozerBeanMapper.class,
	AppConfig.class, 
	PersonaService.class,
	PersonaRepository.class})
@Transactional
public class TestPersona {
	
	@Autowired
	PersonaService myService;

	@Test
	@Rollback
	public void test() {
				
		try {
			Persona p = new Persona();
			p.setNombre("Prueba");
			p.setUserName("test");
			p.setHabilitado(true);
			p.setPassword("sss");
			myService.save(p);

			assertTrue("Debería haberse grabado una persona.", myService.find().size() == 1);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
	}
}
