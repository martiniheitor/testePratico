package controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.ProjetoDTO;
import dto.TarefaDTO;
import entities.Projeto;
import entities.Tarefa;
import services.ProjetoService;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

	@Autowired
	private ProjetoService projetoService;

	// Criar projeto
	@PostMapping
	public ResponseEntity<ProjetoDTO> criarProjeto(@RequestBody ProjetoDTO projetoDTO) {
		Projeto projeto = convertToEntity(projetoDTO);
		Projeto savedProjeto = projetoService.criarProjeto(projeto);
		return ResponseEntity.ok(convertToDTO(savedProjeto));
	}

	// Listar todos os projetos
	@GetMapping
	public ResponseEntity<List<ProjetoDTO>> listarProjetos() {
		List<Projeto> projetos = projetoService.listarProjetos();
		List<ProjetoDTO> projetoDTOs = projetos.stream().map(this::convertToDTO).collect(Collectors.toList());
		return ResponseEntity.ok(projetoDTOs);
	}

	// Buscar projeto por ID
	@GetMapping("/{id}")
	public ResponseEntity<ProjetoDTO> buscarPorId(@PathVariable Long id) {
		Projeto projeto = projetoService.buscarPorId(id);
		return ResponseEntity.ok(convertToDTO(projeto));
	}

	// Atualizar projeto
	@PutMapping("/{id}")
	public ResponseEntity<ProjetoDTO> atualizarProjeto(@PathVariable Long id, @RequestBody ProjetoDTO projetoDTO) {
		Projeto projetoAtualizado = convertToEntity(projetoDTO);
		Projeto updatedProjeto = projetoService.atualizarProjeto(id, projetoAtualizado);
		return ResponseEntity.ok(convertToDTO(updatedProjeto));
	}

	// Conversão de Projeto para ProjetoDTO
	private ProjetoDTO convertToDTO(Projeto projeto) {
		ProjetoDTO dto = new ProjetoDTO();
		dto.setId(projeto.getId());
		dto.setTitulo(projeto.getTitulo());
		dto.setDescricao(projeto.getDescricao());
		dto.setStatus(projeto.getStatus());
		dto.setDataCriacao(projeto.getDataCriacao());
		dto.setTarefas(projeto.getTarefas().stream().map(this::convertToDTO).collect(Collectors.toList()));
		return dto;
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

	// Conversão de ProjetoDTO para Projeto
	private Projeto convertToEntity(ProjetoDTO dto) {
		Projeto projeto = new Projeto();
		projeto.setTitulo(dto.getTitulo());
		projeto.setDescricao(dto.getDescricao());
		projeto.setStatus(dto.getStatus());
		return projeto;
	}
}
