package grp.nfe.controller;

import grp.nfe.model.Produto;
import grp.nfe.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Iterable<Produto>> findAll() {
        var produtos = produtoService.buscarTodosProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Object> findByCodigo(@PathVariable Integer codigo) {
        try {
            var cliente = produtoService.buscarPorCodigo(codigo);
            return ResponseEntity.ok(cliente);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Produto> create(@RequestBody Produto produtoToCreate) {
        var produto = produtoService.create(produtoToCreate);
        return ResponseEntity.ok(produto);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Produto> update(@PathVariable Integer codigo, @RequestBody Produto produtoToUpdate) {
        var produto = produtoService.update(codigo, produtoToUpdate);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Produto> delete(@PathVariable Integer codigo) {
        produtoService.delete(codigo);
        return ResponseEntity.noContent().build();
    }


}
