package edu.unlp.informatica.postgrado.seguimiento.item.repository;

import org.springframework.stereotype.Repository;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;

@Repository
public class ProyectoRepository extends AbstractRepository<Proyecto, Long> {

	public ProyectoRepository() {
		super(Proyecto.class);
	}

	@Override
	public Proyecto getById(Long id) {
		
		return super.getById(id);
	}

	
}