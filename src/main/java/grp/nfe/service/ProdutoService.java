package grp.nfe.service;

import grp.nfe.model.Produto;
import grp.nfe.repository.ItemNotaFiscalRepository;
import grp.nfe.repository.ProdutoRepository;
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

    public Produto create(Produto produto) {
        if (produto.getCodigo() == null || produto.getCodigo().isBlank()) {
            throw new NoSuchElementException("ERRO: Informe o código do Produto.");
        }
        if (produto.getDescricao() == null || produto.getDescricao().isBlank()) {
            throw new IllegalArgumentException("ERRO: Informe a descrição do Produto.");
        }
        if (produto.getValorUnitario() == null || produto.getValorUnitario() < 0) {
            throw new NoSuchElementException("ERRO: Valor unitário não pode ser nulo ou menor que 0");
        }
        if (produtoRepository.findByCodigo(produto.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("ERRO: Já existe produto cadastrado com o mesmo código!");
        }

        return produtoRepository.save(produto);
    }

    public Produto update(String codigo, Produto produtoToUpdate) {
        if (produtoToUpdate.getDescricao() == null || produtoToUpdate.getDescricao().isBlank()) {
            throw new IllegalArgumentException("ERRO: A descrição do produto não pode ser nula ou vazia");
        }
        if (produtoToUpdate.getCodigo() == null || produtoToUpdate.getCodigo().isBlank()) {
            throw new IllegalArgumentException("ERRO: O Produto deve ter um código");
        }
        String novoCodigo = produtoToUpdate.getCodigo();
        if (!codigo.equals(novoCodigo) && produtoRepository.findByCodigo(novoCodigo).isPresent()) {
            throw new IllegalArgumentException("ERRO: Já existe um produto com o código");
        }

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