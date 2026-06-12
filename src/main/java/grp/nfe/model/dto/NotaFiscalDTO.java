package grp.nfe.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class NotaFiscalDTO {

    private String numero;
    private Integer codigoCliente;
    private List<ItemNotaFiscalDTO> itens;

}
