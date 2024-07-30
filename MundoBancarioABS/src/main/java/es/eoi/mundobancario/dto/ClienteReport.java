package es.eoi.mundobancario.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import es.eoi.mundobancario.enums.MovimientosEnum;

@Getter
@Setter
public class ClienteReport {

    private Integer clienteId;
    private String nombre;
    private List<Cuenta> cuentas;

    @Getter
    @Setter
    public static class Cuenta {
        private Integer num_cuenta;
        private String alias;
        private Double saldo;
        private List<Movimiento> movimientos;
        
		public Integer getNumCuenta() {
			return num_cuenta;
		}
		public void setNumCuenta(Integer numCuenta) {
			this.num_cuenta = numCuenta;
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
		public List<Movimiento> getMovimientos() {
			return movimientos;
		}
		public void setMovimientos(List<Movimiento> movimientos) {
			this.movimientos = movimientos;
		}
    }

    @Getter
    @Setter
    public static class Movimiento {
        private Integer id;
        private MovimientosEnum tipo;
        private double importe;
        public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
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
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public LocalDateTime getFecha() {
			return fecha;
		}
		public void setFecha(LocalDateTime fecha) {
			this.fecha = fecha;
		}
		private String description;
        private LocalDateTime fecha;
    }

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
    
    
}
