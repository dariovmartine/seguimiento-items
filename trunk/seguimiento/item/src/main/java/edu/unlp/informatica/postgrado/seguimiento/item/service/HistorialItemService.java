package edu.unlp.informatica.postgrado.seguimiento.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.HistorialItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.HistorialItemRepository;

@Service("historialItemService")
public class HistorialItemService extends AbstractService<HistorialItem, HistorialItemRepository> {

	@Autowired
	private HistorialItemRepository repository;
	
	@Override
	public HistorialItemRepository getRepository() {
		return repository;
	}

}
