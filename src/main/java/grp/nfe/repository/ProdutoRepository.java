package grp.nfe.repository;

import grp.nfe.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByCodigo(Integer codigo);
    Optional<Produto> findByCodigo(Integer codigo);
}
