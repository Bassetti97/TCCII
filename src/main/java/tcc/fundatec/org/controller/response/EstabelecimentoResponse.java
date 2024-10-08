package tcc.fundatec.org.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.fundatec.org.model.Estabelecimento;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoResponse {

    private Long id;
    private String nome;
    private String endereco;
    private String contato;

    public Estabelecimento toEstabelecimento() {
        return Estabelecimento.builder()
                .id(id)
                .nome(nome)
                .endereco(endereco)
                .contato(contato)
                .build();
    }
}
