package grp.nfe.controller;

import grp.nfe.model.NotaFiscal;
import grp.nfe.service.NotaFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/notas")
public class NotaFiscalController {

    @Autowired
    private NotaFiscalService notaFiscalService;

    @GetMapping
    public ResponseEntity<Iterable<NotaFiscal>> findAll() {
        var notas = notaFiscalService.buscarTodas();
        return ResponseEntity.ok(notas);
    }

    @GetMapping("/{numero}")
    public ResponseEntity<Object> findByNumero(@PathVariable Integer numero) {
        try {
            var nota = notaFiscalService.buscarPorNumero(numero);
            return  ResponseEntity.ok(nota);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody NotaFiscal notaFiscal) {
        try {
            var nota = notaFiscalService.create(notaFiscal);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Nota fiscal " + nota.getNumero() + " criada com sucesso para o cliente: " + nota.getCliente().getNome());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{numero}")
    public ResponseEntity<Object> update(@PathVariable Integer numero, @RequestBody NotaFiscal notaFiscal) {
        try {
            var nota = notaFiscalService.update(numero, notaFiscal);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Nota fiscal " + nota.getNumero() + " atualizada!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<Object> delete(@PathVariable Integer numero) {
        try {
            notaFiscalService.delete(numero);
            return ResponseEntity.ok("Nota fiscal " + numero + " excluída com sucesso!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

}
