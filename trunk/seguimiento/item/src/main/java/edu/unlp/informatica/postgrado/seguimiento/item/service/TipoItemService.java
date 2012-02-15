package edu.unlp.informatica.postgrado.seguimiento.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.TipoItemRepository;

@Service("tipoItemService")
public class TipoItemService extends AbstractService<TipoItem, TipoItemRepository> {
	
	@Autowired
	private TipoItemRepository repository;
	

	@Override
	public TipoItemRepository getRepository() {
	
		return repository;
	}

	
}