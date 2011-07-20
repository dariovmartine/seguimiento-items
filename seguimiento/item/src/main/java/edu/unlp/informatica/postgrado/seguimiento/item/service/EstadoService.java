package edu.unlp.informatica.postgrado.seguimiento.item.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.Transformer;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.EstadoRepository;

@Service("estadoService")
@Transactional(propagation = Propagation.SUPPORTS)
public class EstadoService {

	@Autowired
	private DozerBeanMapper mapper; 
		
	@Autowired
	private EstadoRepository estadoRepository;
		
	public Estado save(Estado entity) {
		
		return mapper.map(estadoRepository.save(entity), Estado.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Estado> find() {
		
		return mapper.map(estadoRepository.find(), ArrayList.class);
	}	
	
	public List<Estado> find(int first, int count, String sortParam ) {
	
		return find();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getNames() {
		
		List<String> list = new ArrayList<String>();
		for (Estado estado : find()) {
			list.add(((Estado) estado).getName());
		}
		
		return list;
	}
	
	public int getCount() {
		return estadoRepository.getCount();
	}
	
	public Estado getById(Long id) {
		return mapper.map(estadoRepository.getById(id),Estado.class);
	}
}
