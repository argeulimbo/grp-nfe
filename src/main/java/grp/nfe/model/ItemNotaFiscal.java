package grp.nfe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity(name = "ItemNotaFiscal")
@Table(name = "itensnotafiscal")
public class ItemNotaFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "nota_fiscal_numero")
    private NotaFiscal notaFiscal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_codigo")
    private Produto produto;

    @Column(nullable = false)
    private Integer numeroItem;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, precision = 10, scale = 2)
    private Double totalValor;
}
