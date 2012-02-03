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
import edu.unlp.informatica.postgrado.seguimiento.item.model.security.Usuario;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.UsuarioRepository;

@Service("usuarioService")
public class UsuarioService extends AbstractService<Usuario, UsuarioRepository> {
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UsuarioRepository getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public Usuario findByUsername(String nombre) throws ServiceException {
		
		try {
			DetachedCriteria criteria = getRepository().getCriteria();
			criteria.add( Restrictions.eq("nombre", nombre));
			return getMapper().map(getRepository().findByCriteria(criteria).get(0), Usuario.class);
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}
	}	
}