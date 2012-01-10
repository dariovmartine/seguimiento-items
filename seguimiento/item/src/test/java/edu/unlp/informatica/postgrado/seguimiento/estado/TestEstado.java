package edu.unlp.informatica.postgrado.seguimiento.estado;

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
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.EstadoRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.service.EstadoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={
	AppConfig.class, 
	EstadoService.class,
	EstadoRepository.class})
@Transactional
public class TestEstado {
	
	@Autowired
	EstadoService estadoService;
	
  	@Test
  	@Rollback
	public void test() {
		
		
		try {
			Estado i = new Estado();
			i.setNombre("prueba");
			estadoService.save(i);
			
			assertTrue("Debería haberse grabado un estado.", estadoService.find().size() == 1);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
	}
}
