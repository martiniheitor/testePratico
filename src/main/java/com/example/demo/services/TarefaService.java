package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Tarefa;
import com.example.demo.repositories.TarefaRepository;

@Service
public class TarefaService {

	@Autowired
	private TarefaRepository tarefaRepository;

	// Criar Tarefa
	public Tarefa criarTarefa(Tarefa tarefa) {
		if (tarefa.getProjeto() == null || tarefa.getProjeto().getId() == null) {
			throw new IllegalArgumentException("A tarefa deve estar associada a um projeto existente.");
		}

		validarTarefa(tarefa); // Validação inicial
		return tarefaRepository.save(tarefa); // Salva a tarefa
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

	// Validar Tarefa
	public void validarTarefa(Tarefa tarefa) {
		if (tarefa.getTitulo() == null || tarefa.getTitulo().trim().isEmpty()) {
			throw new IllegalArgumentException("O título da tarefa não pode estar vazio.");
		}
		if (tarefa.getStatus() == null) {
			throw new IllegalArgumentException("O status da tarefa é obrigatório.");
		}
		if (tarefa.getProjeto() == null || tarefa.getProjeto().getId() == null) {
			throw new IllegalArgumentException("A tarefa deve estar associada a um projeto existente.");
		}
	}

}
