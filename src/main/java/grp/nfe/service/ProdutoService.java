package grp.nfe.service;

import grp.nfe.model.Produto;
import grp.nfe.repository.ItemNotaFiscalRepository;
import grp.nfe.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ItemNotaFiscalRepository itemNotaFiscalRepository;

    public Iterable<Produto> buscarTodosProdutos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorCodigo(String codigo) {
        Optional<Produto> produto = produtoRepository.findByCodigo(codigo);
        return produto.orElseThrow(() -> new NoSuchElementException("ERRO: Nenhum produto encontrado com o código fornecido!"));
    }

    public List<Produto> buscarPordescricao(String descricao) {
        return produtoRepository.findByDescricaoContainingIgnoreCase(descricao);
    }

    @Transactional
    public Produto create(Produto produto) {
        if (produtoRepository.findByCodigo(produto.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("ERRO: Já existe produto cadastrado com o mesmo código!");
        }
        if (produto.getValorUnitario() == null || produto.getValorUnitario() < 0) {
            throw new NoSuchElementException("ERRO: Valor unitário não pode ser nulo ou menor que 0");
        }
        if (produto.getDescricao() == null || produto.getDescricao().isBlank()) {
            throw new IllegalArgumentException("ERRO: Informe a descrição do Produto.");
        }
        if (produto.getCodigo() == null || produto.getCodigo().isBlank()) {
            throw new IllegalArgumentException("ERRO: Informe o código do Produto.");
        }
        return produtoRepository.save(produto);
    }

    public Produto update(String codigo, Produto produtoToUpdate) {
        Produto produto =
                produtoRepository.findByCodigo(codigo)
                        .orElseThrow(() -> new IllegalArgumentException("ERRO: Não existe produto cadastrado com este código!"));
        produto.setCodigo(produtoToUpdate.getCodigo());
        produto.setDescricao(produtoToUpdate.getDescricao());
        produto.setValorUnitario(produtoToUpdate.getValorUnitario());
        return produtoRepository.save(produto);
    }

    public void delete(String codigo) {
        Produto produto =
                produtoRepository.findByCodigo(codigo)
                        .orElseThrow(() -> new IllegalArgumentException("ERRO: Não existe produto com o código fornecido para exclusão!"));

        if (itemNotaFiscalRepository.existsByProduto_Codigo(produto.getCodigo())) {
            throw new IllegalStateException("ERRO: Não é possível excluir o produto pois há vínculo com nota.");
        }
        produtoRepository.delete(produto);
    }
}