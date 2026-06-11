package grp.nfe.service;

import grp.nfe.model.Produto;
import grp.nfe.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Iterable<Produto> buscarTodosProdutos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorCodigo(Integer codigo) {
        Optional<Produto> produto = produtoRepository.findByCodigo(codigo);
        return produto.orElseThrow(() -> new NoSuchElementException("Nenhum produto encontrado com o código fornecido!"));
    }

    public Produto buscarPordescricao(String descricao) {
        Optional<Produto> produto = produtoRepository.findByDescricao(descricao);
        return produto.orElseThrow(() -> new NoSuchElementException("Nenhum produto encontraado com o descricao fornecido!"));
    }

    public Produto create(Produto produto) {
        if (produtoRepository.findByCodigo(produto.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Já existe produto cadastrado com o mesmo código!");
        }
        return produtoRepository.save(produto);
    }

    public Produto update(Produto produto) {
        if (!produtoRepository.findByCodigo(produto.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Não existe produto cadastrado com o código fornecido!");
        }
        Produto produtoAntigo = produtoRepository.findByCodigo(produto.getCodigo()).get();
        produtoAntigo.setDescricao(produto.getDescricao());
        produtoAntigo.setValorUnitario(produto.getValorUnitario());
        return produtoRepository.save(produtoAntigo);
    }

    public void delete(Integer codigo) {
        if (codigo == null || !produtoRepository.existsByCodigo(codigo)) {
            throw new NoSuchElementException("Não foi possível encontrar o produto com o código especificado para exclusão!");
        }
    }

}
