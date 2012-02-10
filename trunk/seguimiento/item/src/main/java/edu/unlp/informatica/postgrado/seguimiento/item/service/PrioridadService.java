package edu.unlp.informatica.postgrado.seguimiento.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.mapper.DefaultDozerBeanMapper;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Prioridad;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.PrioridadRepository;

@Service("prioridadService")
public class PrioridadService extends AbstractService<Prioridad, PrioridadRepository> {
	
	@Autowired
	private PrioridadRepository repository;
	
	@Autowired
	private DefaultDozerBeanMapper<Prioridad, PrioridadService> mapper;

	@Override
	public PrioridadRepository getRepository() {

		return repository;
	}

	@Override
	public DefaultDozerBeanMapper<Prioridad, ? extends AbstractService<Prioridad, PrioridadRepository>> getMapper() {

		return mapper;
	}	
}