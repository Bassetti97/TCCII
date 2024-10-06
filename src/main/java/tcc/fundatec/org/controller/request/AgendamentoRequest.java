package tcc.fundatec.org.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoRequest {

    private LocalDateTime dataHorario;
    private String tipoServico;
    private String clienteNome;
    private String estabelecimentoNome;
}
