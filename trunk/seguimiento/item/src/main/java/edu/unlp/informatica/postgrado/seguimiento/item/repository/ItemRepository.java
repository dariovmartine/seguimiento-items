package edu.unlp.informatica.postgrado.seguimiento.item.repository;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;

@Repository
public class ItemRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public List<Item> findItems() {
		
		return null;
	}
	    
	    
	    
}
