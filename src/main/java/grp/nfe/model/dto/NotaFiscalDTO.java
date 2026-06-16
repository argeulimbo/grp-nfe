package grp.nfe.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotaFiscalDTO {

    private Integer numero;
    private Integer codigoCliente;
    private List<ItemNotaFiscalDTO> itens;

    @Data
    public static class ItemNotaFiscalDTO {
        private Integer codigoProduto;
        private Integer quantidade;
    }

}
