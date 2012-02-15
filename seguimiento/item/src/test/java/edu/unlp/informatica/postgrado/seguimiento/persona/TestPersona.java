package edu.unlp.informatica.postgrado.seguimiento.persona;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import org.junit.Assert;
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
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.PersonaRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.service.PersonaService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={
	
	AppConfig.class, 
	PersonaService.class,
	PersonaRepository.class,
	Persona.class})
@Transactional
public class TestPersona {
	
	@Autowired
	PersonaService myService;
	
	@Autowired
	PersonaRepository personaRepository;

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
	
	@Test
	@Rollback
	public void testConversion() {
				
		try {
			Persona p = new Persona();
			p.setNombre("kkk");
			p.setUserName("kkktest");
			p.setHabilitado(true);
			p.setPassword("skkkss");
			p.getRoles().add(Rol.ADMINISTRADOR);
			personaRepository.save(p);
			
			p = personaRepository.getById(p.getId());
			Persona p2 = new Persona(); 
			myService.copyFields(p, p2);

			Assert.assertTrue(p2.getId().equals(p.getId()));
			Assert.assertTrue(p2.getNombre().equals(p.getNombre()));
			Assert.assertTrue(p2.getUserName().equals(p.getUserName()));
			Assert.assertTrue(p2.isHabilitado() == p.isHabilitado()  );
			Assert.assertTrue(p2.getPassword().equals(p.getPassword()));
			Assert.assertTrue(p2.getRoles().equals(p.getRoles()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
	}

	
	
}
