package edu.unlp.informatica.postgrado.seguimiento.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ConfiguracionItemRepository;

@Service("configuracionItemService")
public class ConfiguracionItemService extends AbstractService<ConfiguracionItem, ConfiguracionItemRepository> {
	
	@Autowired
	private ConfiguracionItemRepository repository;
	
	
	@Override
	public ConfiguracionItemRepository getRepository() {

		return repository;
	}


}



