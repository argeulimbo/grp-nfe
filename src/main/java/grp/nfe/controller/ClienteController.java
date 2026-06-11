package grp.nfe.controller;

import grp.nfe.model.Cliente;
import grp.nfe.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Iterable<Cliente>> findAll(){
        var cliente = clienteService.buscarTodosClientes();
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Object> findByCodigo(@PathVariable Integer codigo){
        try {
            var cliente = clienteService.buscarPorCodigo(codigo);
            return ResponseEntity.ok(cliente);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Cliente clienteToCreate) {
        try {
            var cliente = clienteService.create(clienteToCreate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Cliente: " + cliente.getNome() + " criado com sucesso!");
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Object> update(@PathVariable Integer codigo, @RequestBody Cliente clienteToUpdate) {
        try {
            var cliente = clienteService.update(codigo, clienteToUpdate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Cliente " + cliente.getNome() + " com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }

    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Object> delete(@PathVariable Integer codigo) {
        try {
            var clienteDeletado = clienteService.buscarPorCodigo(codigo);
            clienteService.delete(codigo);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Cliente: " + clienteDeletado.getNome() + " excluído com sucesso!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
