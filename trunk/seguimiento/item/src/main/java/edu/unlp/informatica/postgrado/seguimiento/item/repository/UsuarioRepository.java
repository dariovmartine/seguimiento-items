package edu.unlp.informatica.postgrado.seguimiento.item.repository;

import org.springframework.stereotype.Repository;

import edu.unlp.informatica.postgrado.seguimiento.item.model.security.Usuario;

@Repository
public class UsuarioRepository extends AbstractRepository<Usuario, Long> {

	public UsuarioRepository() {
		super(Usuario.class);
	}
}
