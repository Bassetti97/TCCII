package tcc.fundatec.org.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoRequest {

    private Long id;
    private String nome;
    private String endereco;
    private String contato;
}
