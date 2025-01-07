package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import entities.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
