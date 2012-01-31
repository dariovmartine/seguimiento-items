package edu.unlp.informatica.postgrado.seguimiento.item.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@SuppressWarnings("unchecked")
	public void setValues(Proyecto original, Proyecto newVersion) {
		
		Object v = newVersion.getNombre(); 
		if (v != null && ! v.equals(original.getNombre())) {
			original.setNombre((String)v);
		}
		v = newVersion.getIntegrantes();
		if (v != null && ! v.equals(original.getIntegrantes())) {
			original.setIntegrantes((List<Persona>) v);
		}
		v = newVersion.getItems();
		if (v != null && ! v.equals(original.getItems())) {
			original.setItems((List<Item>) v);
		}
		v = newVersion.getLider();
		if (v != null && ! v.equals(original.getLider())) {
			original.setLider((Persona) v);
		}
		v = newVersion.getTipoItemList();
		if (v != null && ! v.equals(original.getTipoItemList())) {
			original.setTipoItemList((List<TipoItem>) v);
		}
	}
	
	@Override
	public Proyecto update(Proyecto entity) throws ServiceException {
		
		Proyecto original = getRepository().getById(entity.getId());
		setValues(original, entity);		
		return super.update(original);
	}
	
	
}
