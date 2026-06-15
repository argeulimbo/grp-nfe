package grp.nfe.controller;

import grp.nfe.service.ItemNotaFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/itens-nota-fiscal")
public class ItemNotaFiscalController {

    @Autowired
    private ItemNotaFiscalService itemNotaFiscalService;

    @GetMapping
    public ResponseEntity<Iterable<Object>> findAll() {
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

}
