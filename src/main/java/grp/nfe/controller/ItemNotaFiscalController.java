package grp.nfe.controller;

import grp.nfe.model.ItemNotaFiscal;
import grp.nfe.service.ItemNotaFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/itens-nota-fiscal")
public class ItemNotaFiscalController {

    @Autowired
    private ItemNotaFiscalService itemNotaFiscalService;

    @GetMapping
    public ResponseEntity<Iterable<ItemNotaFiscal>> findAll() {
            var todosItems = itemNotaFiscalService.findAll();
            return ResponseEntity.ok(todosItems);
    }

    @GetMapping("/{numero}")
    public ResponseEntity<Object> findByNumeroNF(@PathVariable Integer numero) {
        try {
            var itens = itemNotaFiscalService.buscarItensPorNumeroNF(numero);
            return ResponseEntity.ok(itens);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ItemNotaFiscal itemNotaFiscal) {
        try {
            var itemSalvo = itemNotaFiscalService.create(itemNotaFiscal);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Item " + itemSalvo.getNumeroItem() + " adicionado à NF " +
                            itemSalvo.getNotaFiscal().getNumero() + " com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

}
