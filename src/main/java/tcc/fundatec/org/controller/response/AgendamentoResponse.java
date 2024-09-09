package tcc.fundatec.org.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.fundatec.org.model.Agendamento;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoResponse {

    private Long id;
    private LocalDateTime dataHorario;
    private ClienteResponse cliente;
    private EstabelecimentoResponse estabelecimento;

    public Agendamento toAgendamento() {
        return Agendamento.builder()
                .dataHorario(dataHorario)
                .cliente(cliente.toCliente())
                .estabelecimento(estabelecimento.toEstabelecimento())
                .build();
    }
}
