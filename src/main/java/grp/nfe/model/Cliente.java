package grp.nfe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity(name = "Cliente")
@Table(name = "clientes")
public class Cliente {

    private Long id;
    private Integer codigo;
    private String nome;

}
