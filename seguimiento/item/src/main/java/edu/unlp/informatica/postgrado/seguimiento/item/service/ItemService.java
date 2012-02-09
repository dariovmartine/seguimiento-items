package edu.unlp.informatica.postgrado.seguimiento.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ItemRepository;

@Service("itemService")
public class ItemService extends AbstractService<Item, ItemRepository> {
	
	@Autowired
	private ItemRepository repository;

	@Override
	public ItemRepository getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}	
}
