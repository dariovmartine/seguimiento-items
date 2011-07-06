package edu.unlp.informatica.postgrado.seguimiento.item.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ItemRepository;

@Service("itemService")
@Transactional(propagation = Propagation.SUPPORTS)
public class ItemService {

	@Autowired
	private DozerBeanMapper mapper; 
		
	@Autowired
	private ItemRepository itemRepository;
		
	public Item save(Item entity) {
		
		return mapper.map(itemRepository.save(entity), Item.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> find() {
		
		return mapper.map(itemRepository.find(), ArrayList.class);
	}	
	
	public List<Item> find(int first, int count, String sortParam ) {
	
		return find();
	}
	
	public int getCount() {
		return itemRepository.getCount();
	}
	
	public Item getById(Long id) {
		return mapper.map(itemRepository.getById(id),Item.class);
	}
}
