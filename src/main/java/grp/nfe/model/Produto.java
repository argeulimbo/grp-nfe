package grp.nfe.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity(name = "Produto")
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private Integer codigo;

    @Column(nullable = false, length = 100)
    private String descricao;

    @Column(nullable = false)
    private Double valorUnitario;

}
