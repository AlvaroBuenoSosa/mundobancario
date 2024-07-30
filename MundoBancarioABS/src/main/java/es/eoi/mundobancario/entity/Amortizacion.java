package es.eoi.mundobancario.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Amortizaciones")
@Getter
@Setter
@ToString
public class Amortizacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column
	private LocalDateTime fecha;
	
	private Boolean pagada;
	
	@Column(nullable = false)
	private Double importe;
	
	@ManyToOne
	@JsonIgnore
    @JoinColumn(name = "prestamos_id")
    private Prestamo prestamo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(Prestamo prestamo) { 
		this.prestamo = prestamo;
	}

	public Boolean getPagada() {
		return pagada;
	}

	public void setPagada(Boolean pagada) {
		this.pagada = pagada;
	}

	public LocalDateTime getFechaPlanificada() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setFechaPlanificada(LocalDate localDate) {
		// TODO Auto-generated method stub
		
	}
	

	public void setFecha(LocalDate plusMonths) {
		// TODO Auto-generated method stub
		
	}
}

