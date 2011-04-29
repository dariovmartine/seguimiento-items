package edu.unlp.informatica.postgrado.seguimiento.item.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ItemRepository;

@Service("itemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

		
	public Item save(Item entity) {
		
		itemRepository.save(entity);
		return entity;
	}
	
	public List<Item> find() {
		
		return itemRepository.find();
	}	
	
}
