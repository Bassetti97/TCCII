package tcc.fundatec.org.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "profissionais")
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String profissao;

}
