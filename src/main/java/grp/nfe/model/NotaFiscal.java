package grp.nfe.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity(name = "NotaFiscal")
@Table(name = "notafiscal")
public class NotaFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer numero;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_codigo")
    private Cliente cliente;

    @Column
    private LocalDate dataEmissao;

    @Column(nullable = false)
    private Double valorTotal;

    @JsonManagedReference
    @OneToMany(mappedBy = "notaFiscal", cascade = CascadeType.ALL)
    private List<ItemNotaFiscal> itens = new ArrayList<>();


}
