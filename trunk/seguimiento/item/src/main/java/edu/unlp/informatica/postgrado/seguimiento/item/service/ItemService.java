package edu.unlp.informatica.postgrado.seguimiento.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.mapper.DefaultDozerBeanMapper;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ItemRepository;

@Service("itemService")
public class ItemService extends AbstractService<Item, ItemRepository> {
	
	@Autowired
	private ItemRepository repository;
	
	@Autowired
	private DefaultDozerBeanMapper<Item, ItemService> mapper;

	@Override
	public ItemRepository getRepository() {

		return repository;
	}

	@Override
	public DefaultDozerBeanMapper<Item, ? extends AbstractService<Item, ItemRepository>> getMapper() {

		return mapper;
	}	
}
