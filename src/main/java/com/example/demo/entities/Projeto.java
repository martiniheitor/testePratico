package com.example.demo.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.demo.enums.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Projeto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 40)
	private String titulo;

	@Column(nullable = false, length = 255)
	private String descricao;

	@Enumerated(EnumType.ORDINAL)
	private Status status;

	@Column(nullable = false, updatable = false)
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Tarefa> tarefas = new ArrayList<>();

	public Projeto() {
	}

	public Projeto(Long id, String titulo, String descricao, Status status, LocalDateTime dataCriacao,
			List<Tarefa> tarefas) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.status = status;
		this.dataCriacao = dataCriacao;
		this.tarefas = tarefas;
	}
	
	public Projeto(String titulo, String descricao, Status status, List<Tarefa> tarefas) {
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

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
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
		Projeto other = (Projeto) obj;
		return Objects.equals(id, other.id);
	}

}