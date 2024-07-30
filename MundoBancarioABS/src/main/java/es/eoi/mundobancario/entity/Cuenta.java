package es.eoi.mundobancario.entity;

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
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Cuentas")
@Table(name = "Cuentas", schema = "Cuentas")
@Getter
@Setter
@ToString
public class Cuenta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer num_cuenta;

	@Column
	private String alias;
	
	@Column
	private Double saldo;
	

	@ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
	
	@OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL)
    private List<Movimiento> movimiento;
	
	@OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL)
    private List<Prestamo> prestamo;


	public List<Movimiento> getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(List<Movimiento> movimiento) {
		this.movimiento = movimiento;
	}

	public List<Prestamo> getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(List<Prestamo> prestamo) {
		this.prestamo = prestamo;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Integer getNum_cuenta() {
		return num_cuenta;
	}

	public void setNum_cuenta(Integer num_cuenta) {
		this.num_cuenta = num_cuenta;
	}


}
