package edu.unlp.informatica.postgrado.seguimiento.item.repository;

import java.util.Map;

import org.springframework.stereotype.Repository;

import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;

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