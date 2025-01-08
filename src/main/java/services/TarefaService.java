package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.Tarefa;
import repositories.TarefaRepository;

@Service
public class TarefaService {

	@Autowired
	private TarefaRepository tarefaRepository;

	// Criar tarefa
	public Tarefa criarTarefa(Tarefa tarefa) {
		validarTarefa(tarefa);
		return tarefaRepository.save(tarefa);
	}

	// Buscar tarefa por ID
	public Tarefa buscarPorId(Long id) {
		return tarefaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada com ID: " + id));
	}

	// Atualizar tarefa
	public Tarefa atualizarTarefa(Long id, Tarefa tarefaAtualizada) {
		validarTarefa(tarefaAtualizada);
		Tarefa tarefa = buscarPorId(id);

		tarefa.setId(id);
		tarefa.setTitulo(tarefaAtualizada.getTitulo());
		tarefa.setDescricao(tarefaAtualizada.getDescricao());
		tarefa.setStatus(tarefaAtualizada.getStatus());

		return tarefaRepository.save(tarefa);
	}

	public void validarTarefa(Tarefa tarefa) {
		// Valida se o título está vazio
		if (tarefa.getTitulo() == null || tarefa.getTitulo().trim().isEmpty()) {
			throw new IllegalArgumentException("O título da tarefa não pode estar vazio.");
		}

		// Valida o status
		if (tarefa.getStatus() == null) {
			throw new IllegalArgumentException("O status da tarefa é obrigatório.");
		}

		// Garante que a tarefa esteja associada a um projeto
		if (tarefa.getProjeto() == null) {
			throw new IllegalArgumentException("A tarefa deve estar associada a um projeto.");
		}
	}
}
