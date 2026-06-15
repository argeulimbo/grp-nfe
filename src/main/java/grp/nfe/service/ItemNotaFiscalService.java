package grp.nfe.service;

import grp.nfe.model.ItemNotaFiscal;
import grp.nfe.model.NotaFiscal;
import grp.nfe.model.Produto;
import grp.nfe.repository.ItemNotaFiscalRepository;
import grp.nfe.repository.NotaFiscalRepository;
import grp.nfe.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ItemNotaFiscalService {

    @Autowired
    private ItemNotaFiscalRepository itemNotaFiscalRepository;

    @Autowired
    private NotaFiscalRepository notaFiscalRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Iterable<Object> findAll() {
        return itemNotaFiscalRepository.findAll();
    }

    public ItemNotaFiscal buscarItensPorNumeroNF(Integer numero){
        Optional<ItemNotaFiscal> itemNotaFiscal = itemNotaFiscalRepository.findById(numero);
        return itemNotaFiscal.orElseThrow(() -> new NoSuchElementException("Registro não encontrado!"));
    }

    @Transactional
    public ItemNotaFiscal create(ItemNotaFiscal itemNotaFiscal) {
        if (!notaFiscalRepository.findByNumero(itemNotaFiscal.getNotaFiscal().getNumero()).isPresent()) {
            throw new NoSuchElementException("Nota fiscal não encontrada!");
        }
        NotaFiscal nota = notaFiscalRepository.findByNumero(itemNotaFiscal.getNotaFiscal().getNumero())
                .orElseThrow(() -> new NoSuchElementException("Nota fiscal não encontrada!"));
        Produto produto = produtoRepository.findByCodigo(itemNotaFiscal.getProduto().getCodigo())
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado"));

        int numeroDoProximoItem = itemNotaFiscalRepository
                .findByNotaFiscalNumeroOrderByNumeroItem(nota.getNumero())
                .size() + 1;

        Double valorTotal = produto.getValorUnitario() * itemNotaFiscal.getQuantidade();

        ItemNotaFiscal novoItem = new ItemNotaFiscal(
                null,
                nota,
                produto,
                numeroDoProximoItem,
                itemNotaFiscal.getQuantidade(),
                valorTotal
        );
        ItemNotaFiscal itemSalvo = itemNotaFiscalRepository.save(novoItem);

        List<ItemNotaFiscal> todosItens = itemNotaFiscalRepository
                .findByNotaFiscalNumeroOrderByNumeroItem(nota.getNumero());

        Double novoTotal = todosItens.stream()
                .map(ItemNotaFiscal::getTotalValor)
                .reduce(0.0, Double::sum);

        nota.setValorTotal(novoTotal);
        notaFiscalRepository.save(nota);
        return itemSalvo;

    }

}
