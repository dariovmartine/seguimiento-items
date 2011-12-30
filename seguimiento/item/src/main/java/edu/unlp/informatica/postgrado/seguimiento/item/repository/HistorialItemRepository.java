package edu.unlp.informatica.postgrado.seguimiento.item.repository;

import org.springframework.stereotype.Repository;

import edu.unlp.informatica.postgrado.seguimiento.item.model.HistorialItem;

@Repository
public class HistorialItemRepository extends AbstractRepository<HistorialItem, Long> {

	public HistorialItemRepository() {
		super(HistorialItem.class);
	}

	
	
}
