package edu.unlp.informatica.postgrado.seguimiento.tipoitem;

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
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.TipoItemRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.service.TipoItemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={
	
	AppConfig.class, 
	TipoItemService.class,
	TipoItemRepository.class})
@Transactional
public class TestTipoItem {

	@Autowired
	TipoItemService myService;
	
	@Test
	@Rollback
	public void test() {
		
		try {
			TipoItem i = new TipoItem();
			i.setNombre("Reporte de bug");

			myService.save(i);
			
			assertTrue("Debería haberse grabado un tipo de item.", myService.find().size() == 1);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
	}
}
