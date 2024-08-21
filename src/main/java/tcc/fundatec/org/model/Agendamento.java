package tcc.fundatec.org.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataHorario;

    @ManyToOne
    private Cliente clientes;
    @ManyToOne
    private Profissional profissionais;


}
