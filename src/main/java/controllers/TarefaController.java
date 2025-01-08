package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.TarefaDTO;
import entities.Tarefa;
import services.TarefaService;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

	@Autowired
	private TarefaService tarefaService;

	// Criar tarefa
	@PostMapping
	public ResponseEntity<TarefaDTO> criarTarefa(@RequestBody TarefaDTO tarefaDTO) {
		Tarefa tarefa = convertToEntity(tarefaDTO);
		Tarefa savedTarefa = tarefaService.criarTarefa(tarefa);
		return ResponseEntity.ok(convertToDTO(savedTarefa));
	}

	// Buscar tarefa por ID
	@GetMapping("/{id}")
	public ResponseEntity<TarefaDTO> buscarPorId(@PathVariable Long id) {
		Tarefa tarefa = tarefaService.buscarPorId(id);
		return ResponseEntity.ok(convertToDTO(tarefa));
	}

	// Atualizar tarefa
	@PutMapping("/{id}")
	public ResponseEntity<TarefaDTO> atualizarTarefa(@PathVariable Long id, @RequestBody TarefaDTO tarefaDTO) {
		Tarefa tarefaAtualizada = convertToEntity(tarefaDTO);
		Tarefa updatedTarefa = tarefaService.atualizarTarefa(id, tarefaAtualizada);
		return ResponseEntity.ok(convertToDTO(updatedTarefa));
	}

	// Conversão de Tarefa para TarefaDTO
	private TarefaDTO convertToDTO(Tarefa tarefa) {
		TarefaDTO dto = new TarefaDTO();
		dto.setId(tarefa.getId());
		dto.setTitulo(tarefa.getTitulo());
		dto.setDescricao(tarefa.getDescricao());
		dto.setStatus(tarefa.getStatus());
		return dto;
	}

	// Conversão de TarefaDTO para Tarefa
	private Tarefa convertToEntity(TarefaDTO dto) {
		Tarefa tarefa = new Tarefa();
		tarefa.setTitulo(dto.getTitulo());
		tarefa.setDescricao(dto.getDescricao());
		tarefa.setStatus(dto.getStatus());
		return tarefa;
	}
}
