package edu.unlp.informatica.postgrado.seguimiento.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.mapper.DefaultDozerBeanMapper;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ProyectoRepository;

@Service("proyectoService")
public class ProyectoService extends AbstractService<Proyecto, ProyectoRepository> {
		
	@Autowired
	private ProyectoRepository repository;
	
	

	@Override
	public ProyectoRepository getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}

	@Override
	public void beforeInitialize(DefaultDozerBeanMapper<Proyecto> mapper) {
				
		mapper.getMapperBuilders().get(Proyecto.class).getFieldsToExcluded().add("items");
	}	
}
