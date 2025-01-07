package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import entities.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
