package es.eoi.mundobancario.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Prestamos")
@Getter
@Setter
@ToString
public class Prestamo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column
	private String descripcion;
	
	@Column
	private LocalDateTime fecha;

	@Column
	private Double importe;
	
	@Column
	private Integer plazos;

	@ManyToOne
	@JsonIgnore
    @JoinColumn(name = "num_cuenta")
    private Cuenta cuenta;
	
	@OneToMany(mappedBy = "prestamo", cascade = CascadeType.ALL)
    private List<Amortizacion> amortizacion;

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

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Integer getPlazos() {
		return plazos;
	}

	public void setPlazos(Integer plazos) {
		this.plazos = plazos;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public List<Amortizacion> getAmortizacion() {
		return amortizacion;
	}

	public void setAmortizacion(List<Amortizacion> amortizacion) {
		this.amortizacion = amortizacion;
	}

	public void setFechaSolicitud(LocalDate now) {

		
	}
}
