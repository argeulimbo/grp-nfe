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

    public Cliente update(Cliente cliente) {
        if (!clienteRepository.findByCodigo(cliente.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Não existe cliente cadastrado com este código!");
        }
        Cliente clienteAntigo = clienteRepository.findByCodigo(cliente.getCodigo()).get();
        clienteAntigo.setNome(cliente.getNome());
        return clienteRepository.save(clienteAntigo);
    }

    public void delete(Integer codigo) {
        if (codigo == null || !clienteRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("Não foi possível encontrar o cliente com o código especificado para exclusão!");
        } else {
            Cliente cliente = clienteRepository.findByCodigo(codigo).orElse(null);
            clienteRepository.delete(cliente);
        }
    }

}
