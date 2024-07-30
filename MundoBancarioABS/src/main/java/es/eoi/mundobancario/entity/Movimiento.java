package es.eoi.mundobancario.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.eoi.mundobancario.enums.MovimientosEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Movimientos")
@Getter
@Setter
@ToString
public class Movimiento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column
	private String descripcion;
	
	@Column
	private LocalDateTime fecha;
	
	@Column
	private double importe;
	
	@Enumerated(EnumType.STRING)
    private MovimientosEnum tipo;

	
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "num_cuenta")
	private Cuenta cuenta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	 public void setFecha(LocalDateTime fechaInicio) {
	        this.fecha = fechaInicio;
	    }

	public MovimientosEnum getTipo() {
		return tipo;
	}

	public void setTipo(MovimientosEnum tipo) {
		this.tipo = tipo;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}
	
	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	



}

