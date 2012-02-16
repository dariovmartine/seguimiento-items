package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
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

import edu.unlp.informatica.postgrado.seguimiento.item.service.HistorialItemService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.MappingOptions;
import edu.unlp.informatica.postgrado.seguimiento.item.validators.ValidUserName;

@Entity
@Access(AccessType.FIELD)
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
	@MappingOptions
	Long id;
	
	@ValidUserName
	@NotNull
	@Column(name = "TITULO", unique=true)
	@MappingOptions
	private String titulo;
	
	@NotNull
	@Column(name = "DESCRIPCION")
	@MappingOptions(exclude={HistorialItemService.class})
	private String descripcion;
		
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_ESTADO")
	@MappingOptions(exclude={HistorialItemService.class})
	private Estado estado;	
		
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_PERSONA")
	@MappingOptions(exclude={HistorialItemService.class})
	private Persona responsable;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_PRIORIDAD")
	@MappingOptions(exclude={HistorialItemService.class})
	private Prioridad prioridad;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_ITEM")
	@MappingOptions(exclude={HistorialItemService.class})
	private TipoItem tipoItem;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_PROYECTO")
	@MappingOptions(exclude={HistorialItemService.class})
	private Proyecto proyecto;
	
	@OneToMany(mappedBy="item", cascade= CascadeType.ALL)
	@MappingOptions(exclude={HistorialItemService.class})
	private List<HistorialItem> historial = new ArrayList<HistorialItem>();
	
	@Transient //hasta ver con Dary si va o no
	private Date fechaCarga;

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
		
		cambiarEstado(estado, responsable, "");
	}
	
	private void cambiarEstado(Estado nuevoEstado, Persona quienLoCambia, String comentario) {
		
		if (estado == null && proyecto != null && ! proyecto.isStateInitial(tipoItem, nuevoEstado)) {			
			throw new IllegalArgumentException("El estado al que se intenta cambiar no es válido.");
		}		
		
		if (estado != null && estado.equals(nuevoEstado)) {
			throw new IllegalArgumentException("El item ya se encuentra en ese estado.");
		}
		//valida si la persona q quiere cambiar el estado puede hacerlo
		
		//valida q el estado al que va a pasar sea valido. 
		//Se valida aca? se valida en el setEstado? en ambos lados?
		if (proyecto != null && getEstado() != null) {	
			if (! proyecto.canChangeState(tipoItem, estado, nuevoEstado)) {			
				throw new IllegalArgumentException("El estado al que se intenta cambiar no es válido.");
			}
			
			//guardar historial
			HistorialItem historicoNuevo = new HistorialItem();
			historicoNuevo.setEstado(nuevoEstado);
			Date fechaCambio = new Date(new Date().getTime());
			historicoNuevo.setFechaInicio(fechaCambio);
			historicoNuevo.setFechaFin(null);
			historicoNuevo.setItem(this);
			historicoNuevo.setResponsable(getResponsable());//esta bien esto????
			
			//esta cambiando de un estado a otro
			//aca habria q poner la fecha de fin al estado anterior
			
			//buscar el ultimo estado en el que esta en la lista de historial
			HistorialItem actual = this.buscarHistorialActual();
			if (actual != null){
				actual.setFechaFin(fechaCambio);
				actual.setComentario(comentario);
			}
			
			historial.add(historicoNuevo);
		}		
		
		this.estado = nuevoEstado;
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
		if (proyecto == null || proyecto.canUseTipoItem(tipoItem)) {
			this.tipoItem = tipoItem;
		} else
			throw new IllegalArgumentException("El tipo de item '" + tipoItem + "' no existe en el proyecto");
		
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		
		if (proyecto == null || proyecto.canAddItem(this)) {
			this.proyecto = proyecto;
		} else
			throw new IllegalArgumentException("El tipo de item '" + this.tipoItem + "' no existe en el proyecto");
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
		if (! (obj instanceof Item ))
			return false;
		Item other = (Item) obj;
		if (descripcion == null) {
			if (other.getDescripcion() != null)
				return false;
		} else if (!descripcion.equals(other.getDescripcion()))
			return false;
//		if (estado == null) {      <<<<<----  no tiene sentido, dos item en distinto estado "son iguales"
//			if (other.estado != null)
//				return false;
//		} else if (!estado.equals(other.estado))
//			return false;
		if (prioridad == null) {
			if (other.getPrioridad() != null)
				return false;
		} else if (!prioridad.equals(other.getPrioridad()))
			return false;
		if (proyecto == null) {
			if (other.getProyecto() != null)
				return false;
		} else if (!proyecto.equals(other.getProyecto()))
			return false;
		if (responsable == null) {
			if (other.getResponsable() != null)
				return false;
		} else if (!responsable.equals(other.getResponsable()))
			return false;
		if (tipoItem == null) {
			if (other.getTipoItem() != null)
				return false;
		} else if (!tipoItem.equals(other.getTipoItem()))
			return false;
		if (titulo == null) {
			if (other.getTitulo() != null)
				return false;
		} else if (!titulo.equals(other.getTitulo()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return titulo;
	}

	public Date getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
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
