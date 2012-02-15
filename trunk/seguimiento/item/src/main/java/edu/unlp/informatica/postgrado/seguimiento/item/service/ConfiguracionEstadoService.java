package edu.unlp.informatica.postgrado.seguimiento.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ConfiguracionEstadoRepository;

@Service("configuracionEstadoService")
public class ConfiguracionEstadoService extends AbstractService<ConfiguracionEstado, ConfiguracionEstadoRepository> {
	
	@Autowired
	private ConfiguracionEstadoRepository repository;
	
	
	@Override
	public ConfiguracionEstadoRepository getRepository() {

		return repository;
	}

	
	
}
