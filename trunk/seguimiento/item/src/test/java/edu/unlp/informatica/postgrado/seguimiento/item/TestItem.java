package edu.unlp.informatica.postgrado.seguimiento.item;

import static edu.unlp.informatica.postgrado.seguimiento.item.model.TipoEstado.FINAL;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.TipoEstado.INICIAL;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.TipoEstado.INTERMEDIO;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import edu.unlp.informatica.postgrado.seguimiento.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Prioridad;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ConfiguracionItemRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.EstadoRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ItemRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.PersonaRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.PrioridadRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ProyectoRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.TipoItemRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ConfiguracionItemService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.EstadoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ItemService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.PersonaService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.PrioridadService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ProyectoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.TipoItemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={
	
	AppConfig.class, 
	PersonaService.class,
	ProyectoService.class,
	TipoItemService.class, 
	EstadoService.class, 
	ConfiguracionItemService.class,
	ItemService.class,
	PrioridadService.class,
	PersonaRepository.class,
	ProyectoRepository.class,
	TipoItemRepository.class, 
	EstadoRepository.class, 
	ConfiguracionItemRepository.class,
	ItemRepository.class,
	PrioridadRepository.class
})
@Transactional
public class TestItem {

	@Autowired
	PersonaService myService;
	
	@Autowired
	ProyectoService proService;
	
	@Autowired
	TipoItemService tiService; 
	
	@Autowired
	EstadoService eService; 
	
	@Autowired
	ConfiguracionItemService ciService; 
		
	@Autowired
	ItemService iService;
	
	@Autowired
	PrioridadService prService;
	
	@Autowired 
	HibernateTemplate hibernateTemplate;
	
	Persona per;
	Prioridad pr;
	TipoItem ti;
	Estado e1, e2, e3;
	ConfiguracionEstado confEstado;
	Proyecto p;
	
	Item i;

	private Persona usu;
	
	@Before
	public void setUp(){
		try {
			per = new Persona();
			per.setNombre("Jefi");
			per.setUserName("test");
			per.setHabilitado(true);
			per.setPassword("sss");
			per.getRoles().add(Rol.LIDER_DE_PROYECTO);
			myService.save(per);
			
			usu = new Persona();
			usu.setNombre("Jefi2");
			usu.setUserName("test2");
			usu.setHabilitado(true);
			usu.setPassword("sss2");
			usu.getRoles().add(Rol.DESARROLLADOR);
			myService.save(usu);
			
			pr = new Prioridad();
			pr.setNombre("Altisi3");
			prService.save(pr);
						
			ti = new TipoItem();
			ti.setNombre("Ampliació3");
			tiService.save(ti);
			
			e1 = new Estado();
			e1.setNombre("inicial");
			e1.setTipoEstado(INICIAL);
			eService.save(e1);
			
			e2 = new Estado();
			e2.setNombre("en proceso");
			e2.setTipoEstado(INTERMEDIO);
			eService.save(e2);
			
			e3 = new Estado();
			e3.setNombre("finalizado");
			e3.setTipoEstado(FINAL);
			eService.save(e3);
			
			confEstado = new ConfiguracionEstado();
			ConfiguracionItem ci = new ConfiguracionItem();
			ci.getProximosEstados().put(e1, confEstado);
			ci.setTipoItem(ti);
			confEstado.setConfiguracionItem(ci);
			confEstado.getProximosEstados().add(e2);
			
			ConfiguracionEstado confEstado2 = new ConfiguracionEstado();
			ci.getProximosEstados().put(e2, confEstado2);
			confEstado2.setConfiguracionItem(ci);
			confEstado2.getProximosEstados().add(e3);
			
			//ciService.save(ci);
			
			
			p = new Proyecto();
			p.setLider(per);
			p.setNombre("ppp2");
			p.getTipoItems().put(ti, ci);
			p.getIntegrantes().add(usu);
			ci.setProyecto(p);
			
			
			proService.save(p);
									
			i = new Item();
			i.setTipoItem(ti);
			i.setProyecto(p);
			i.setEstado(e1);
			i.setFechaCarga(new Date());
			i.setTitulo("prueba");
			i.setDescripcion("descrip");
			i.setPrioridad(pr);
			i.setResponsable(per);
			
			
			i =    iService.save(i);
				i.toString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Test
	@Rollback
	public void test() {
		
		try {
			
			Item i1 = new Item();
			i1.setTitulo("lu3333");
			i1.setProyecto(p);
			i1.setPrioridad(pr);
			i1.setDescripcion("ss");
			i1.setResponsable(usu);
			i1.setTipoItem(ti);
			i1.setEstado(e1);
			iService.save(i1);
			
			i1 = iService.getById(i1.getId());
			
			assertTrue(i1.getTitulo().equals("lu3333"));
			assertTrue("Debería haberse grabado dos items.", myService.find().size() == 2);	
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	@Rollback
	public void tetsCambioEstadook() {
		try {
			int cantHistInicial, cantHistFinal;
			cantHistInicial = i.getHistorial().size();
			i.setEstado(e2);
			i = iService.update(i);
			cantHistFinal = i.getHistorial().size();
			
			assertTrue("Debería habercambiado de estado", i.getEstado().equals(e2) && cantHistInicial == cantHistFinal-1);
			
			cantHistInicial = i.getHistorial().size();
			i.setEstado(e3);
			iService.update(i);
			cantHistFinal = i.getHistorial().size();
			
			assertTrue("Debería habercambiado de estado", i.getEstado().equals(e3) && cantHistInicial == cantHistFinal-1);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
