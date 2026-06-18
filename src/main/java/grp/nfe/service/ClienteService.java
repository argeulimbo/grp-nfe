package grp.nfe.service;

import grp.nfe.model.Cliente;
import grp.nfe.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Iterable<Cliente> buscarTodosClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorCodigo(String codigo) {
        Optional<Cliente> cliente = clienteRepository.findByCodigo(codigo);
        return cliente.orElseThrow(() -> new NoSuchElementException("ERRO: Nenhum cliente encontrado com o código fornecido"));
    }

    public List<Cliente> buscarPorNome(String nome) {
        return  clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Cliente create(Cliente cliente) {
        if (clienteRepository.findByCodigo(cliente.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("ERRO: Já existe cliente cadastrado com o mesmo código");
        }

        // Valida codigo
        if (cliente.getCodigo() == null || cliente.getCodigo().isBlank()) {
            throw new IllegalArgumentException("ERRO: O código do cliente não pode ser nulo ou menor que 0");
        }

        // Valida nome
        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new IllegalArgumentException("ERRO: O nome do cliente não pode ser nulo ou vazio.");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente update(String codigo, Cliente clienteToUpdate) {
        if (clienteToUpdate.getNome() == null || clienteToUpdate.getNome().isBlank()) {
            throw new IllegalArgumentException("ERRO: O nome não foi informado para alterar!");
        }
        Cliente cliente =
                clienteRepository.findByCodigo(codigo)
                        .orElseThrow(() -> new IllegalArgumentException("ERRO: Não existe cliente cadastrado com este código!"));
        cliente.setNome(clienteToUpdate.getNome());
        return clienteRepository.save(cliente);
    }

    public void delete(String codigo) {
        Cliente cliente =
                clienteRepository.findByCodigo(codigo)
                        .orElseThrow(() -> new IllegalArgumentException("ERRO: Não existe cliente com o código fornecido para exclusão!"));
        clienteRepository.delete(cliente);
    }
}