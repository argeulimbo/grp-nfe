package grp.nfe.service;

import grp.nfe.model.Cliente;
import grp.nfe.model.ItemNotaFiscal;
import grp.nfe.model.NotaFiscal;
import grp.nfe.model.Produto;
import grp.nfe.model.dto.NotaFiscalDTO;
import grp.nfe.repository.ItemNotaFiscalRepository;
import grp.nfe.repository.NotaFiscalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class NotaFiscalService {

    @Autowired
    private NotaFiscalRepository notaFiscalRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemNotaFiscalRepository itemNotaFiscalRepository;

    public Iterable<NotaFiscal> buscarTodas() {
        return notaFiscalRepository.findAll();
    }

    public NotaFiscal buscarPorNumero(Integer numero) {
        return notaFiscalRepository.findByNumero(numero)
                .orElseThrow(() -> new NoSuchElementException("ERRO: Nenhuma nota fiscal encontrada!"));
    }

    public NotaFiscal create(NotaFiscalDTO notaFiscal) {
        if (notaFiscal.getNumero() == null) {
            throw new IllegalArgumentException("ERRO: Número da nota é obrigatório!");
        }
        if (notaFiscal.getCodigoCliente() == null) {
            throw new IllegalArgumentException("ERRO: O código do cliente é obrigatório!");
        }
        if (notaFiscal.getItens() == null) {
            throw new IllegalArgumentException("ERRO: A NF deve conter ao menos um item!");
        }

        if (notaFiscalRepository.findByNumero(notaFiscal.getNumero()).isPresent()) {
            throw new IllegalArgumentException("ERRO: Já existe nota cadastrada com este número!");
        }
        if (notaFiscal.getItens().isEmpty()) {
            throw new IllegalArgumentException("ERRO: A NF deve conter ao menos um item!");
        }
        Cliente cliente =
                clienteService.buscarPorCodigo(notaFiscal.getCodigoCliente());

        NotaFiscal nota =  new NotaFiscal();
        nota.setNumero(notaFiscal.getNumero());
        nota.setCliente(cliente);
        nota.setDataEmissao(LocalDate.now());
        nota.setValorTotal(0.0);

        notaFiscalRepository.save(nota);

        int numeroItem = 1;
        Double totalNota = 0.0;

        for (NotaFiscalDTO.ItemNotaFiscalDTO itemDto : notaFiscal.getItens()) {
            if (itemDto.getCodigoProduto() == null) {
                throw new IllegalArgumentException("ERRO: Código do produto é obrigatório!");
            }
            if (itemDto.getQuantidade() == null || itemDto.getQuantidade() <= 0) {
                throw new IllegalArgumentException("ERRO: Quantidade inválida no item!");
            }

            Produto produto = produtoService.buscarPorCodigo(itemDto.getCodigoProduto());
            Double totalItem = produto.getValorUnitario() * itemDto.getQuantidade();

            ItemNotaFiscal item = new ItemNotaFiscal(
                    null,
                    nota,
                    produto,
                    numeroItem,
                    itemDto.getQuantidade(),
                    totalItem
            );
            itemNotaFiscalRepository.save(item);
            totalNota +=  totalItem;
            numeroItem++;
        }
        nota.setValorTotal(totalNota);
        return notaFiscalRepository.save(nota);
    }

    public NotaFiscal update(Integer numero, NotaFiscal notaFiscalToUpdate) {
        try {
            NotaFiscal notaAntiga = buscarPorNumero(numero);
            notaAntiga.setNumero(notaFiscalToUpdate.getNumero());
            return notaFiscalRepository.save(notaAntiga);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("ERRO: Nenhuma nota fiscal encontrada!");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void delete(Integer numero) {
        NotaFiscal notaFiscalDeletar =
                notaFiscalRepository.findByNumero(numero)
                        .orElseThrow(() -> new NoSuchElementException("ERRO: Não existe nota fiscal cadastrada com este número."));
        notaFiscalRepository.delete(notaFiscalDeletar);
    }

}
