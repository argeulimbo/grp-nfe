package grp.nfe.controller;

import grp.nfe.model.Cliente;
import grp.nfe.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Cliente> findByCodigo(@PathVariable Integer codigo){
        var cliente = clienteService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente clienteToCreate) {
        var cliente = clienteService.create(clienteToCreate);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Cliente> update(@PathVariable Integer codigo, @RequestBody Cliente clienteToUpdate) {
        var cliente = clienteService.update(codigo, clienteToUpdate);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Cliente> delete(@PathVariable Integer codigo) {
        clienteService.delete(codigo);
        return ResponseEntity.noContent().build();
    }



}
