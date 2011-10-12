package edu.unlp.informatica.postgrado.seguimiento.item.service;

import java.util.ArrayList;
import java.util.List;

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
		// TODO Auto-generated method stub
		return repository;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<String> getNames() throws ServiceException {
		
		List<String> list = new ArrayList<String>();
		for (Estado estado : find()) {
			list.add(((Estado) estado).getName());
		}
		
		return list;
	}	
}
