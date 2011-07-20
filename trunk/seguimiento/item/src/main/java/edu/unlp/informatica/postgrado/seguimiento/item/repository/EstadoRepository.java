package edu.unlp.informatica.postgrado.seguimiento.item.repository;

import org.springframework.stereotype.Repository;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;

@Repository
public class EstadoRepository extends AbstractRepository<Estado, Long> {

	public EstadoRepository() {
		super(Estado.class);
	}

	
}
