package grp.nfe.repository;

import grp.nfe.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCodigo(String codigo);
    Optional<Produto> findByDescricao(String descricao);

    List<Produto> findByDescricaoContainingIgnoreCase(String descricao);
}
