package edu.unlp.informatica.postgrado.seguimiento.item.service;

import static edu.unlp.informatica.postgrado.seguimiento.item.model.TipoEstado.INICIAL;

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
import edu.unlp.informatica.postgrado.seguimiento.item.repository.EstadoRepository;

@Service("estadoService")
public class EstadoService extends AbstractService<Estado, EstadoRepository> {
	
	@Autowired
	private EstadoRepository repository;
	
		
	@Override
	public EstadoRepository getRepository() {

		return repository;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<String> getNames() throws ServiceException {
		
		List<String> list = new ArrayList<String>();
		for (Estado estado : find()) {
			list.add(((Estado) estado).getNombre());
		}
		
		return list;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public List<Estado> findEstadosIniciales() throws ServiceException {
		
		try {
			DetachedCriteria criteria = getRepository().getCriteria();
			criteria.add( Restrictions.eq("tipoEstado", INICIAL));
			
			List<Estado> res = new ArrayList<Estado>();
			copyFields(getRepository().findByCriteria(criteria), res);
			
			return res;
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}
	}
}
