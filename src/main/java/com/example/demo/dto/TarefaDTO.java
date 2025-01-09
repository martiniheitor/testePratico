package com.example.demo.dto;

import java.util.Objects;

import com.example.demo.entities.Projeto;
import com.example.demo.enums.Status;

public class TarefaDTO {

	private Long id;
	private String titulo;
	private String descricao;
	private Status status;
	private Projeto projeto;

	public TarefaDTO() {
	}

	public TarefaDTO(Long id, String titulo, String descricao, Status status, Projeto projeto) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.status = status;
		this.projeto = projeto;
	}

	public TarefaDTO(String titulo, String descricao, Status status, Projeto projeto) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.status = status;
		this.projeto = projeto;
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

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
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
		TarefaDTO other = (TarefaDTO) obj;
		return Objects.equals(id, other.id);
	}

}