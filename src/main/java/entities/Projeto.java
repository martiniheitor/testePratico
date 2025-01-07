package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import enums.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 40)
    private String titulo;
    
    @Column(nullable = false, length = 255)
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarefa> tarefas = new ArrayList<>();
}