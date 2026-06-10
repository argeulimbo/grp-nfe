package grp.nfe.service;

import grp.nfe.model.Cliente;
import grp.nfe.repository.ClienteRepository;
import jakarta.validation.ValidationException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorCodigo(Integer codigo) {
        Optional<Cliente> cliente = clienteRepository.findByCodigo(codigo);
        return cliente.orElseThrow(() -> new NoSuchElementException("Cliente não encontrado: " + codigo));
    }

    public Cliente create(Cliente cliente) {
        if (cliente.getCodigo() == null) {
            throw new IllegalArgumentException("Código do cliente é obrigatório!");
        }
        if (cliente.getNome() == null || cliente.getNome() == "" || cliente.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório!");
        }
        if (clienteRepository.existByCodigo(cliente.getCodigo())) {
            throw new IllegalArgumentException("Já existe um cliente cadastro com o código fornecido: " + cliente.getCodigo());
        }
        return clienteRepository.save(cliente);
    }

    public Cliente update(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().isBlank() || cliente.getNome() == "") {
            throw new IllegalArgumentException("Nome do cliente é obrigatório!");
        }
        Cliente clienteAntigo = buscarPorCodigo(cliente.getCodigo());
        clienteAntigo.setNome(cliente.getNome());
        return clienteRepository.save(clienteAntigo);
    }

    public void delete(Integer codigo) {
        Cliente cliente =  buscarPorCodigo(codigo);
        clienteRepository.delete(cliente);
    }



}
