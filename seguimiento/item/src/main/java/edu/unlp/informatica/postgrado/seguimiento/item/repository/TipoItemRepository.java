package edu.unlp.informatica.postgrado.seguimiento.item.repository;

import org.springframework.stereotype.Repository;

import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
@Repository
public class TipoItemRepository extends AbstractRepository<TipoItem, Long> {

	public TipoItemRepository() {
		super(TipoItem.class);
	}

	
}