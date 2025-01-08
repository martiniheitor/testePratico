package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.Projeto;
import entities.Tarefa;
import repositories.ProjetoRepository;

@Service
public class ProjetoService {

	@Autowired
	private ProjetoRepository projetoRepository;

	@Autowired
	private TarefaService tarefaService;

	// Criar projeto
	public Projeto criarProjeto(Projeto projeto) {
		validarProjeto(projeto);
		return projetoRepository.save(projeto);
	}

	// Listar todos os projetos
	public List<Projeto> listarProjetos() {
		return projetoRepository.findAll();
	}

	// Buscar projeto por ID
	public Projeto buscarPorId(Long id) {
		return projetoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado com ID: " + id));
	}

	// Atualizar projeto
	public Projeto atualizarProjeto(Long id, Projeto projetoAtualizado) {
		validarProjeto(projetoAtualizado);
		Projeto projeto = buscarPorId(id);

		projeto.setId(id);
		projeto.setTitulo(projetoAtualizado.getTitulo());
		projeto.setDescricao(projetoAtualizado.getDescricao());
		projeto.setStatus(projetoAtualizado.getStatus());

		return projetoRepository.save(projeto);
	}

	private void validarProjeto(Projeto projeto) {
		// Valida se o título está vazio
		if (projeto.getTitulo() == null || projeto.getTitulo().trim().isEmpty()) {
			throw new IllegalArgumentException("O título do projeto não pode estar vazio.");
		}

		// Valida o status
		if (projeto.getStatus() == null) {
			throw new IllegalArgumentException("O status do projeto é obrigatório.");
		}

		// Valida se o projeto contém pelo menos uma tarefa
		if (projeto.getTarefas() == null || projeto.getTarefas().isEmpty()) {
			throw new IllegalArgumentException("Um projeto deve conter pelo menos uma tarefa.");
		}

		// Valida cada tarefa associada ao projeto
		for (Tarefa tarefa : projeto.getTarefas()) {
			tarefaService.validarTarefa(tarefa);

			// Garante que a tarefa esteja relacionada ao projeto
			if (tarefa.getProjeto() == null || !tarefa.getProjeto().equals(projeto)) {
				throw new IllegalArgumentException("Cada tarefa deve estar associada ao projeto.");
			}

			// Verifica se a tarefa existente possui ID válido
			if (tarefa.getId() == null) {
				throw new IllegalArgumentException("As tarefas existentes devem conter um ID válido.");
			}
		}
	}
}
