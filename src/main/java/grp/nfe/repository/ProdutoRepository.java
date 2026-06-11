package grp.nfe.repository;

import grp.nfe.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCodigo(Integer codigo);
    Optional<Produto> findByDescricao(String descricao);

    boolean existsByCodigo(Integer codigo);
}
