/**
 * 
 */
package edu.unlp.informatica.postgrado.seguimiento.item.model;

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



/**
 * @author lu
 *
 */
@Entity
@Table(name = "HISTORIA_ITEM")
	
public class HistorialItem {

	
	@Id 
	@Column(name = "ID")
	@GeneratedValue(generator="HIST_ITEM_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="HIST_ITEM_ID_GEN", sequenceName="SEQ_HIST_ITEM_ID")
	Long id;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_PERSONA")
	private Persona responsable;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_ITEM")
	private Item item;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_ESTADO")
	private Persona estado;
	
	@NotNull
	@Column(name = "F_INICIO")
	private Timestamp fechaInicio;
	
	@NotNull
	@Column(name = "F_FIN")
	private Timestamp fechaFin;
}
