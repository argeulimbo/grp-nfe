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

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorCodigo(Integer codigo) {
        Optional<Produto> produto = produtoRepository.findByCodigo(codigo);
        return  produto.orElseThrow(() -> new NoSuchElementException("Produto não encontrado - código: " + codigo));
    }

    public Produto create(Produto produto) {
        if (produto.getCodigo() == null) {
            throw new IllegalArgumentException("O código do produto é obrigatório!");
        }
        if (produto.getDescricao() == null || produto.getDescricao().trim().equals("") || produto.getDescricao().isBlank()) {
            throw new IllegalArgumentException("A descrição do produto é obrigatória!");
        }
        if (produtoRepository.existsByCodigo(produto.getCodigo())) {
            throw new IllegalArgumentException("Já existe um produto cadastrado com o mesmo código: " + produto.getCodigo());
        }
        return  produtoRepository.save(produto);
    }

    public Produto update(Produto produto) {
        if (produto.getCodigo() == null || produto.getCodigo() == "" || produto.getCodigo()) {

        }
    }

    public void delete(Integer codigo) {
        Produto produto =  buscarPorCodigo(codigo);
        produtoRepository.delete(produto);
    }


}
