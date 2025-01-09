package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.example.demo.enums.Status;

public class ProjetoDTO {

	private Long id;
	private String titulo;
	private String descricao;
	private Status status;
	private LocalDateTime dataCriacao;
	private List<TarefaDTO> tarefas;

	public ProjetoDTO() {
	}

	public ProjetoDTO(Long id, String titulo, String descricao, Status status, LocalDateTime dataCriacao,
			List<TarefaDTO> tarefas) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.status = status;
		this.dataCriacao = dataCriacao;
		this.tarefas = tarefas;
	}
	
	public ProjetoDTO(String titulo, String descricao, Status status, List<TarefaDTO> tarefas) {
	    this.titulo = titulo;
	    this.descricao = descricao;
	    this.status = status;
	    this.tarefas = tarefas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public List<TarefaDTO> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<TarefaDTO> tarefas) {
		this.tarefas = tarefas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjetoDTO other = (ProjetoDTO) obj;
		return Objects.equals(id, other.id);
	}

}