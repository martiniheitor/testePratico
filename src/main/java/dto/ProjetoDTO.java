package dto;

import java.time.LocalDateTime;

import enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjetoDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private Status status;
    private LocalDateTime dataCriacao;
    
}