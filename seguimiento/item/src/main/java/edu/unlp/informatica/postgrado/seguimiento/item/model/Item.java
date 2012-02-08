package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.validators.ValidUserName;

@Entity
@Table(name = "ITEM")
public class Item implements Serializable, Numerable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5039797189743799468L;

	@Id 
	@Column(name = "ID")
	@GeneratedValue(generator="ITEM_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ITEM_ID_GEN", sequenceName="SEQ_ITEM_ID", allocationSize=1, initialValue=1)
	Long id;
	
	@ValidUserName
	@NotNull
	@Column(name = "TITULO", unique=true)
	private String titulo;
	
	@NotNull
	@Column(name = "DESCRIPCION")
	private String descripcion;
		
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_ESTADO")
	private Estado estado;	
		
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_PERSONA")
	private Persona responsable;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_PRIORIDAD")
	private Prioridad prioridad;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_TIPO_ITEM")
	private TipoItem tipoItem;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_PROYECTO")
	private Proyecto proyecto;
	
	@Transient //hasta ver con Dary si va o no
	private Timestamp fechaCarga;
	
	@OneToMany(mappedBy="item")
	List<HistorialItem> historial = new ArrayList<HistorialItem>();
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		if (proyecto != null) {
			if (!proyecto.isPersonInPoject(responsable)){
				throw new IllegalArgumentException("La persona que intenta asignar como responsable no forma parte del proyecto.");
			}
		}
		this.responsable = responsable;
	}

	public Prioridad getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Prioridad prioridad) {
		this.prioridad = prioridad;
	}
	
	public TipoItem getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(TipoItem tipoItem) {
		this.tipoItem = tipoItem;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public List<HistorialItem> getHistorial() {
		return historial;
	}

	public void setHistorial(List<HistorialItem> historial) {
		this.historial = historial;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result
				+ ((prioridad == null) ? 0 : prioridad.hashCode());
		result = prime * result
			+ ((proyecto == null) ? 0 : proyecto.hashCode());		
		result = prime * result
				+ ((responsable == null) ? 0 : responsable.hashCode());
		result = prime * result
				+ ((tipoItem == null) ? 0 : tipoItem.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
		Item other = (Item) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (prioridad == null) {
			if (other.prioridad != null)
				return false;
		} else if (!prioridad.equals(other.prioridad))
			return false;
		if (proyecto == null) {
			if (other.proyecto != null)
				return false;
		} else if (!proyecto.equals(other.proyecto))
			return false;
		if (responsable == null) {
			if (other.responsable != null)
				return false;
		} else if (!responsable.equals(other.responsable))
			return false;
		if (tipoItem == null) {
			if (other.tipoItem != null)
				return false;
		} else if (!tipoItem.equals(other.tipoItem))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return titulo;
	}

	public Timestamp getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(Timestamp fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	
	public void cambiarEstado(Estado nuevoEstado, Persona quienLoCambia, String comentario){
		//valida si la persona q quiere cambiar el estado puede hacerlo
		
		
		
		//valida q el estado al que va a pasar sea valido. 
		//Se valida aca? se valida en el setEstado? en ambos lados?
		if (proyecto != null && getEstado() != null && estado != null && ! getEstado().equals(estado) ) {	
			if (! proyecto.canChangeState(tipoItem, getEstado(), estado)) {			
				throw new IllegalArgumentException("El estado al que se intenta cambiar no es válido.");
			}
		}
		
		
		//guardar historial
		HistorialItem historicoNuevo = new HistorialItem();
		historicoNuevo.setEstado(nuevoEstado);
		Timestamp fechaCambio = new Timestamp(new Date().getTime());
		historicoNuevo.setFechaInicio(fechaCambio);
		historicoNuevo.setItem(this);
		historicoNuevo.setResponsable(getResponsable());//esta bien esto????
		
		
		if(estado == null){
			//es el primer estado, se supone q se esta creando el item
		}else{
			//esta cambiando de un estado a otro
			//aca habria q poner la fecha de fin al estado anterior
			
			//buscar el ultimo estado en el que esta en la lista de historial
			HistorialItem actual = this.buscarHistorialActual();
			if (actual != null){
				actual.setFechaFin(fechaCambio);
				actual.setComentario(comentario);
			}
		}
		this.getHistorial().add(historicoNuevo);
		
		//aca se setea el estado propiamente
		this.setEstado(nuevoEstado);
	}
	
	private HistorialItem buscarHistorialActual(){
		for (HistorialItem hist : this.getHistorial()) {
			if (hist.getEstado().equals(estado) && hist.getFechaFin() == null){
				return hist;
			}
		}
		return null;
	}
}
