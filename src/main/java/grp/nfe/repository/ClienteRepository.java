package grp.nfe.repository;

import grp.nfe.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existByCodigo(Integer codigo);
    Optional<Cliente> findByCodigo(Integer codigo);

}
