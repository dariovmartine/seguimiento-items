package edu.unlp.informatica.postgrado.seguimiento.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
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

	@Override
	public ConfiguracionEstado save(ConfiguracionEstado entity)
			throws ServiceException {
		throw new ServiceException("No pueden crearse directamente una configuración de estado.");
	}

	@Override
	public ConfiguracionEstado update(ConfiguracionEstado entity)
			throws ServiceException {

		if (entity != null && entity.getId() != null) {
			return super.update(entity);
		}
		
		throw new ServiceException("No pueden crearse directamente una configuración de estado.");
	}

	@Override
	public void delete(ConfiguracionEstado entity) throws ServiceException {

		throw new ServiceException("No pueden eliminarse directamente una configuración de estado.");
	}
	
	
	
}
