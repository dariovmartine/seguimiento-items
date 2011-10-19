package edu.unlp.informatica.postgrado.seguimiento.item.repository;

import org.springframework.stereotype.Repository;

import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;

@Repository
public class ConfiguracionItemRepository extends AbstractRepository<ConfiguracionItem, Long> {

	public ConfiguracionItemRepository() {
		super(ConfiguracionItem.class);
	}	
}


