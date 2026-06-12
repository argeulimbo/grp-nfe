package grp.nfe.controller;

import grp.nfe.model.Produto;
import grp.nfe.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/buscar")
    public ResponseEntity<List<Produto>> findByDescricao(@RequestParam String descricao) {
        List<Produto> produtos =
                produtoService.buscarPordescricao(descricao);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(produtos);
        }
        return ResponseEntity.ok(produtos);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Produto produtoToCreate) {
        try {
            var produto = produtoService.create(produtoToCreate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto: " + produto.getDescricao() + " criado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Object> update(@PathVariable Integer codigo, @RequestBody Produto produtoToUpdate) {
        try {
            var produto = produtoService.update(codigo, produtoToUpdate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto: " + produto.getDescricao() + " atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Object> delete(@PathVariable Integer codigo) {
        try {
            var produtoDeletado = produtoService.buscarPorCodigo(codigo);
            produtoService.delete(produtoDeletado.getCodigo());
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto: " + produtoDeletado.getDescricao() + " excluído com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}