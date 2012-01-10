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
		
		Proyecto byId = super.getById(id);
		Map<TipoItem, ConfiguracionItem> tipoItems = byId.getTipoItems();
		for (TipoItem ti : tipoItems.keySet()) {
			ti.toString();
		} 
		
		return byId;
	}

	
}