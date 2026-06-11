package grp.nfe.repository;

import grp.nfe.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCodigo(Integer codigo);
    Optional<Cliente> findByNome(String nome);

    boolean existsByCodigo(Integer codigo);
}
