package grp.nfe.repository;

import grp.nfe.model.ItemNotaFiscal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemNotaFiscalRepository extends JpaRepository<ItemNotaFiscal,Integer> {

    List<ItemNotaFiscal> findByNotaFiscalNumeroOrderByNumeroItem(Integer codigo);

}
