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
    private String tipoServico;
    private AgendamentoClienteResponse cliente;
    private AgendamentoEstabelecimentoResponse estabelecimento;

    public Agendamento toAgendamento() {
        return Agendamento.builder()
                .id(id)
                .dataHorario(dataHorario)
                .tipoServico(tipoServico)
                .cliente(cliente != null ? cliente.toCliente() : null)
                .estabelecimento(estabelecimento != null ? estabelecimento.toEstabelecimento() : null)
                .build();
    }
}
