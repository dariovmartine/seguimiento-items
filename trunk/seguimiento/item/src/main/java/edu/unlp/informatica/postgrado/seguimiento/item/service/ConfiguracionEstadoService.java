package edu.unlp.informatica.postgrado.seguimiento.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.mapper.DefaultDozerBeanMapper;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
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
	public void beforeInitialize(DefaultDozerBeanMapper<ConfiguracionEstado> mapper) {
									
		mapper.getMapperBuilders().get(Proyecto.class).getFieldsToExcluded().add("integrantes");
		mapper.getMapperBuilders().get(Proyecto.class).getFieldsToExcluded().add("lider");
		mapper.getMapperBuilders().get(Proyecto.class).getFieldsToExcluded().add("tipoItems");
		mapper.getMapperBuilders().get(Proyecto.class).getFieldsToExcluded().add("items");
	}	
}
