package edu.unlp.informatica.postgrado.seguimiento.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.mapper.DefaultDozerBeanMapper;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ProyectoRepository;

@Service("proyectoService")
public class ProyectoService extends AbstractService<Proyecto, ProyectoRepository> {
		
	@Autowired
	private ProyectoRepository repository;
	
	@Autowired
	private DefaultDozerBeanMapper<Proyecto, ProyectoService> mapper;
	
	@Override
	public ProyectoRepository getRepository() {

		return repository;
	}
	
	@Override
	public DefaultDozerBeanMapper<Proyecto, ? extends AbstractService<Proyecto, ProyectoRepository>> getMapper() {

		return mapper;
	}
}
