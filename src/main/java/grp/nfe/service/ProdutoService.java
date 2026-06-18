package grp.nfe.service;

import grp.nfe.model.Produto;
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
        if (produtoRepository.findByCodigo(produto.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("ERRO: Já existe produto cadastrado com o mesmo código!");
        }
        return produtoRepository.save(produto);
    }

    public Produto update(String codigo, Produto produtoToUpdate) {
        Produto produto =
                produtoRepository.findByCodigo(codigo)
                        .orElseThrow(() -> new IllegalArgumentException("ERRO: Não existe produto cadastrado com este código!"));
        produto.setDescricao(produtoToUpdate.getDescricao());
        produto.setValorUnitario(produtoToUpdate.getValorUnitario());
        return produtoRepository.save(produto);
    }

    public void delete(String codigo) {
        Produto produto =
                produtoRepository.findByCodigo(codigo)
                        .orElseThrow(() -> new IllegalArgumentException("ERRO: Não existe produto com o código fornecido para exclusão!"));
        produtoRepository.delete(produto);
    }
}