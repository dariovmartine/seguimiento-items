package edu.unlp.informatica.postgrado.seguimiento.item.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ProyectoRepository;

@Service("proyectoService")
public class ProyectoService extends AbstractService<Proyecto, ProyectoRepository> {
		
	@Autowired
	private ProyectoRepository repository;

	@Override
	public ProyectoRepository getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}
}
