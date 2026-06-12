package grp.nfe.service;

import grp.nfe.model.Cliente;
import grp.nfe.model.NotaFiscal;
import grp.nfe.repository.NotaFiscalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NotaFiscalService {

    @Autowired
    private NotaFiscalRepository notaFiscalRepository;

    @Autowired
    private ClienteService clienteService;

    public Iterable<NotaFiscal> buscarTodas() {
        return notaFiscalRepository.findAll();
    }

    public NotaFiscal buscarPorNumero(Integer numero) {
        Optional<NotaFiscal> notaFiscal = notaFiscalRepository.findByNumero(numero);
        return notaFiscal.orElseThrow(() -> new  NoSuchElementException("Nenhuma nota fiscal encontrada"));
    }

    public NotaFiscal create(NotaFiscal notaFiscal) {
        if (notaFiscalRepository.findByNumero(notaFiscal.getNumero()).isPresent()) {
            throw new IllegalArgumentException("Já existe nota fiscal cadastrada com este numero");
        }
        Cliente cliente = clienteService.buscarPorCodigo(notaFiscal.getCliente().getCodigo());
        notaFiscal.setCliente(cliente);
        notaFiscal.setDataEmissao(LocalDate.now());
        notaFiscal.setValorTotal(BigDecimal.ZERO);
        return notaFiscalRepository.save(notaFiscal);
    }

    public NotaFiscal update(Integer numero, NotaFiscal notaFiscalToUpdate) {
        NotaFiscal notaFiscal =
                notaFiscalRepository.findByNumero(numero)
                        .orElseThrow(() -> new NoSuchElementException("Não existe nota fiscal cadastrada com este número!"));
        NotaFiscal notaAntiga = buscarPorNumero(numero);
        notaAntiga.setNumero(notaFiscalToUpdate.getNumero());
        return notaFiscalRepository.save(notaAntiga);

    }

    public void delete(Integer numero) {
        NotaFiscal notaFiscalDeletar =
                notaFiscalRepository.findByNumero(numero)
                        .orElseThrow(() -> new NoSuchElementException("Não existe nota fiscal cadastrada com este número."));
        notaFiscalRepository.delete(notaFiscalDeletar);
    }

}
