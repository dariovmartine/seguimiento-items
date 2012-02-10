package edu.unlp.informatica.postgrado.seguimiento.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.mapper.DefaultDozerBeanMapper;
import edu.unlp.informatica.postgrado.seguimiento.item.model.HistorialItem;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.HistorialItemRepository;

@Service("historialItemService")
public class HistorialItemService extends AbstractService<HistorialItem, HistorialItemRepository> {

	@Autowired
	private HistorialItemRepository repository;
	
	@Autowired
	private DefaultDozerBeanMapper<HistorialItem, HistorialItemService> mapper;
	
	@Override
	public HistorialItemRepository getRepository() {
		return repository;
	}

	@Override
	public DefaultDozerBeanMapper<HistorialItem, ? extends AbstractService<HistorialItem, HistorialItemRepository>> getMapper() {

		return mapper;
	}
}
