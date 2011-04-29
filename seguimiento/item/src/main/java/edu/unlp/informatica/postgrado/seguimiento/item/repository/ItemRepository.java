package edu.unlp.informatica.postgrado.seguimiento.item.repository;

import org.springframework.stereotype.Repository;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;

@Repository
public class ItemRepository extends AbstractRepository<Item, Long> {

	public ItemRepository() {
		super(Item.class);
	}
}
