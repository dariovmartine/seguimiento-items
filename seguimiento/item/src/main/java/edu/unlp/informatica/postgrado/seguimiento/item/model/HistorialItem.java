/**
 * 
 */
package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;
import java.sql.Timestamp;

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

import org.apache.commons.lang.time.DateUtils;

import edu.unlp.informatica.postgrado.seguimiento.item.mapper.MappingOptions;



/**
 * @author lu
 *
 */
@Entity
@Table(name = "HISTORIAL_ITEM")
	
public class HistorialItem implements Serializable, Numerable{

	private static final long serialVersionUID = -5069221919900164546L;

	public void setId(Long id) {
		this.id = id;
	}

	@Id 
	@Column(name = "ID")
	@GeneratedValue(generator="HIST_ITEM_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="HIST_ITEM_ID_GEN", sequenceName="SEQ_HIST_ITEM_ID", allocationSize=1, initialValue=1)
	@MappingOptions(order=1)
	Long id;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_PERSONA")
	@MappingOptions(order=2)
	private Persona responsable;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_ITEM")
	@MappingOptions(order=3)
	private Item item;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_ESTADO")
	@MappingOptions(order=4)
	private Estado estado;
	
	@NotNull
	@Column(name = "F_INICIO")
	@MappingOptions(order=5)
	private Timestamp fechaInicio;
	
	@NotNull
	@Column(name = "F_FIN")
	@MappingOptions(order=6)
	private Timestamp fechaFin;
	
	@Column(name = "COMENTARIO")
	@MappingOptions(order=7)
	private String comentario;

	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Long getId() {
		return id;
	}
	
	public String toString(){
		String result="";
		if (this.item != null)
			result += "item "+item.toString();
		if (this.estado != null)
			result += " estado "+estado.toString();
		if (fechaInicio != null)
			result += " desde "+fechaInicio.toString();
		if (fechaFin != null)
			result += " hasta "+fechaFin.toString();
		return result; //item.toString()+" "+estado.toString()+" "+fechaInicio.toString()+" "+fechaFin.toString();
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((comentario == null) ? 0 : comentario.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result
				+ ((fechaFin == null) ? 0 : fechaFin.hashCode());
		result = prime * result
				+ ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result
				+ ((responsable == null) ? 0 : responsable.hashCode());
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
		HistorialItem other = (HistorialItem) obj;
		if (comentario == null) {
			if (other.comentario != null)
				return false;
		} else if (!comentario.equals(other.comentario))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaFin == null) {
			if (other.fechaFin != null)
				return false;
		} else if (!DateUtils.isSameDay(fechaFin,other.fechaFin))
			return false;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!DateUtils.isSameDay(fechaInicio,other.fechaInicio))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (responsable == null) {
			if (other.responsable != null)
				return false;
		} else if (!responsable.equals(other.responsable))
			return false;
		return true;
	}
	
	
	
}
