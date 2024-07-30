package es.eoi.mundobancario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer>{
	
	List<Prestamo> findByCuenta(Integer num_cuenta);
	
	boolean existsByCuentaAndAmortizacionIsNull(Cuenta cuenta);
	
	
}
