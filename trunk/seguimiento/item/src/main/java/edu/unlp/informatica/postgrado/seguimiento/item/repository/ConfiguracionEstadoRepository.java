package edu.unlp.informatica.postgrado.seguimiento.item.repository;

import org.springframework.stereotype.Repository;

import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;

@Repository
public class ConfiguracionEstadoRepository extends AbstractRepository<ConfiguracionEstado, Long> {

	public ConfiguracionEstadoRepository() {
		super(ConfiguracionEstado.class);
	}
}
