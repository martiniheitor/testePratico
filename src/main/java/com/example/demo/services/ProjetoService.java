package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Projeto;
import com.example.demo.entities.Tarefa;
import com.example.demo.repositories.ProjetoRepository;

@Service
public class ProjetoService {

	@Autowired
	private ProjetoRepository projetoRepository;

	@Autowired
	private TarefaService tarefaService;


	// Criar Projeto
	@Transactional
	public Projeto criarProjeto(Projeto projeto) {
	    // Validações iniciais
	    if (projeto.getTitulo() == null || projeto.getTitulo().trim().isEmpty()) {
	        throw new IllegalArgumentException("O título do projeto não pode estar vazio.");
	    }
	    if (projeto.getStatus() == null) {
	        throw new IllegalArgumentException("O status do projeto é obrigatório.");
	    }

	    // Salva o projeto inicialmente sem tarefas
	    Projeto projetoSalvo = projetoRepository.save(projeto);

	    // Associa manualmente as tarefas ao projeto salvo
	    List<Tarefa> tarefasAssociadas = new ArrayList<>();
	    if (projeto.getTarefas() != null && !projeto.getTarefas().isEmpty()) {
	        for (Tarefa tarefa : projeto.getTarefas()) {
	            tarefa.setProjeto(projetoSalvo); // Define o projeto na tarefa
	            // Salva cada tarefa, mas agora dentro de uma transação controlada
	            Tarefa tarefaSalva = tarefaService.criarTarefa(tarefa); // Salva a tarefa
	            tarefasAssociadas.add(tarefaSalva); // Adiciona a tarefa salva à lista
	        }
	    }

	    // Associa as tarefas ao projeto salvo
	    projetoSalvo.setTarefas(tarefasAssociadas);
	    
	    // Não precisamos salvar o projeto novamente, pois já foi salvo na transação
	    validarProjeto(projetoSalvo); // Validação final

	    return projetoSalvo;
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

	// Validar Projeto
	private void validarProjeto(Projeto projeto) {
		if (projeto.getTitulo() == null || projeto.getTitulo().trim().isEmpty()) {
			throw new IllegalArgumentException("O título do projeto não pode estar vazio.");
		}
		if (projeto.getStatus() == null) {
			throw new IllegalArgumentException("O status do projeto é obrigatório.");
		}
		if (projeto.getTarefas() == null || projeto.getTarefas().isEmpty()) {
			throw new IllegalArgumentException("Um projeto deve conter pelo menos uma tarefa.");
		}

		for (Tarefa tarefa : projeto.getTarefas()) {
			if (tarefa.getProjeto() == null || !tarefa.getProjeto().equals(projeto)) {
				throw new IllegalArgumentException("Cada tarefa deve estar associada ao projeto.");
			}
			tarefaService.validarTarefa(tarefa); // Validação de cada tarefa
		}
	}

}
