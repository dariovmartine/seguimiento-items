package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TIPO_ITEM_PROYECTO")
public class TipoItemProyecto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3690185332798501964L;

	@Id 
	@Column(name = "ID")
	@GeneratedValue(generator="TIPO_ITEM_PROYECTO_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="TIPO_ITEM_PROYECTO_ID_GEN", sequenceName="SEQ_TIPO_ITEM_PROYECTO_ID")
	private Long id;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_PROYECTO")
	private Proyecto proyecto;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_TIPO_ITEM")
	private TipoItem tipoItem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public TipoItem getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(TipoItem tipoItem) {
		this.tipoItem = tipoItem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((proyecto == null) ? 0 : proyecto.hashCode());
		result = prime * result
				+ ((tipoItem == null) ? 0 : tipoItem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoItemProyecto other = (TipoItemProyecto) obj;
		if (proyecto == null) {
			if (other.proyecto != null)
				return false;
		} else if (!proyecto.equals(other.proyecto))
			return false;
		if (tipoItem == null) {
			if (other.tipoItem != null)
				return false;
		} else if (!tipoItem.equals(other.tipoItem))
			return false;
		return true;
	}
	
	
}
