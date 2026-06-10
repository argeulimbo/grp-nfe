package grp.nfe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity(name = "NotaFiscal")
@Table(name = "notafiscal")
public class NotaFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(optional = false)
    private LocalDate dataEmissao;

    @Column(nullable = false)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @OneToMany(mappedBy = "notafiscal", cascade = CascadeType.ALL)
    private List<ItemNotaFiscal> itens = new ArrayList<>();

}
