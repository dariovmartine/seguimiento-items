package edu.unlp.informatica.postgrado.seguimiento.item.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.PersonaRepository;

@Service("personaService")
public class PersonaService extends AbstractService<Persona, PersonaRepository> {
	
	@Autowired
	private PersonaRepository repository;
	
	@Override
	public PersonaRepository getRepository() {
		
		return repository;
	}	
		
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public Persona findByUsername(String nombre) throws ServiceException {
		
		try {
			DetachedCriteria criteria = getRepository().getCriteria();
			criteria.add( Restrictions.eq("userName", nombre));
			
			Persona entity = (Persona) getRepository().findByCriteria(criteria).get(0);
			Persona res = new Persona();
			copyFields(entity, res);	
			
			return res;
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}
	}
}