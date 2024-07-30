package es.eoi.mundobancario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.eoi.mundobancario.entity.Cuenta;

@Repository
public interface CuentasRepository extends JpaRepository<Cuenta, Integer>{

	List<Cuenta> findAllBySaldoLessThan(double saldo);
}
