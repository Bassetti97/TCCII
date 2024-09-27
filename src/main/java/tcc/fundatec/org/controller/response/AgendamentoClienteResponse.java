package tcc.fundatec.org.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.fundatec.org.model.Cliente;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoClienteResponse {

    private Long id;
    private String nome;

    public Cliente toCliente() {
        return Cliente.builder()
                .id(id)
                .nome(nome)
                .build();
    }
}
