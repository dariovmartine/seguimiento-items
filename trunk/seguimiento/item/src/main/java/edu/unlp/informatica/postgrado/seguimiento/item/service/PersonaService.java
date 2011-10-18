package edu.unlp.informatica.postgrado.seguimiento.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.PersonaRepository;

@Service("personaService")
public class PersonaService extends AbstractService<Persona, PersonaRepository> {
	
	@Autowired
	private PersonaRepository repository;

	@Override
	public PersonaRepository getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}	
}