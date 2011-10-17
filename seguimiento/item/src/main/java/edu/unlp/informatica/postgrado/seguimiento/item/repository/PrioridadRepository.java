package edu.unlp.informatica.postgrado.seguimiento.item.repository;

import org.springframework.stereotype.Repository;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Prioridad;

@Repository
public class PrioridadRepository extends AbstractRepository<Prioridad, Long> {

	public PrioridadRepository() {
		super(Prioridad.class);
	}	
}