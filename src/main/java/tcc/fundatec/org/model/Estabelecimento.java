package tcc.fundatec.org.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "estabelecimento")
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cnpj;
    private String logradouro;
    private String razaoSocial;
    private String contato;

}
