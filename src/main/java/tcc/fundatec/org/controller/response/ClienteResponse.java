package tcc.fundatec.org.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.fundatec.org.model.Cliente;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String logradouro;
    private String complemento;
    private String cep;
    private String telefone;
    private String email;

    public Cliente toCliente() {
        return Cliente.builder()
                .id(id)
                .nome(nome)
                .cpf(cpf)
                .dataNascimento(dataNascimento)
                .logradouro(logradouro)
                .complemento(complemento)
                .cep(cep)
                .telefone(telefone)
                .email(email)
                .build();
    }
}
