package edu.unlp.informatica.postgrado.seguimiento.item.repository;

import org.springframework.stereotype.Repository;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;

@Repository
public class PersonaRepository extends AbstractRepository<Persona, Long> {

	public PersonaRepository() {
		super(Persona.class);
	}	
}
