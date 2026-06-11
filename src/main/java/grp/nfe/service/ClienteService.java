package grp.nfe.service;

import grp.nfe.model.Cliente;
import grp.nfe.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Iterable<Cliente> buscarTodosClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorCodigo(Integer codigo) {
        Optional<Cliente> cliente = clienteRepository.findByCodigo(codigo);
        return cliente.orElseThrow(() -> new NoSuchElementException("Nenhum cliente encontrado com o código fornecido"));
    }

    public Cliente buscarPorNome(String nome) {
        Optional<Cliente> cliente = clienteRepository.findByNome(nome);
        return cliente.orElseThrow(() -> new NoSuchElementException("Nenhum cliente encontrado com o nome fornecido!"));
    }

    public Cliente create(Cliente cliente) {
        if (clienteRepository.findByCodigo(cliente.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Já existe cliente cadastrado com o mesmo código!");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente update(Integer codigo, Cliente clienteToUpdate) {
        Cliente cliente =
                clienteRepository.findByCodigo(codigo)
                        .orElseThrow(() -> new IllegalArgumentException("Não existe cliente cadastrado com este código!"));
        cliente.setNome(clienteToUpdate.getNome());
        return clienteRepository.save(cliente);
    }

    public void delete(Integer codigo) {
        Cliente cliente =
                clienteRepository.findByCodigo(codigo)
                        .orElseThrow(() -> new IllegalArgumentException("Não existe cliente com o código fornecido para exclusão!"));
        clienteRepository.delete(cliente);
    }

}
