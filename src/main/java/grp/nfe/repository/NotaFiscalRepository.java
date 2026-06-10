package grp.nfe.repository;

import grp.nfe.model.NotaFiscal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long> {

    boolean existsByNumero(String numero);
    Optional<NotaFiscal> findByNumero(String numero);

}
