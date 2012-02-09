package edu.unlp.informatica.postgrado.seguimiento.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.mapper.DefaultDozerBeanMapper;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ConfiguracionItemRepository;

@Service("configuracionItemService")
public class ConfiguracionItemService extends AbstractService<ConfiguracionItem, ConfiguracionItemRepository> {
	
	@Autowired
	private ConfiguracionItemRepository repository;

	@Override
	public ConfiguracionItemRepository getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}
	
	@Override
	public void beforeInitialize(DefaultDozerBeanMapper<ConfiguracionItem> mapper) {
				
		mapper.getMapperBuilders().get(Proyecto.class).getFieldsToExcluded().add("integrantes");
		mapper.getMapperBuilders().get(Proyecto.class).getFieldsToExcluded().add("lider");
		mapper.getMapperBuilders().get(Proyecto.class).getFieldsToExcluded().add("tipoItems");
		mapper.getMapperBuilders().get(Proyecto.class).getFieldsToExcluded().add("items");
	}		
}



