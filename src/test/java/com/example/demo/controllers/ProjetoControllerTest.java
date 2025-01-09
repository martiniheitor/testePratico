package com.example.demo.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.ProjetoDTO;
import com.example.demo.entities.Projeto;
import com.example.demo.enums.Status;
import com.example.demo.services.ProjetoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProjetoController.class)
public class ProjetoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private ProjetoService projetoService;

	@InjectMocks
	private ProjetoController projetoController;

	@Test
	public void testCriarProjeto_ComDadosValidos_DeveRetornarStatusOk() throws Exception {
		ProjetoDTO projetoDTO = new ProjetoDTO();
		projetoDTO.setTitulo("Projeto 1");
		projetoDTO.setDescricao("Descrição do Projeto 1");
		projetoDTO.setStatus(Status.INICIADO);

		Projeto projeto = new Projeto("Projeto 1", "Descrição do Projeto 1", Status.INICIADO, new ArrayList<>());

		when(projetoService.criarProjeto(any(Projeto.class))).thenReturn(projeto);

		mockMvc.perform(post("/projetos").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(projetoDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.titulo").value("Projeto 1"))
				.andExpect(jsonPath("$.descricao").value("Descrição do Projeto 1"));
	}

	@Test
	public void testListarProjetos_DeveRetornarTodosOsProjetos() throws Exception {
		Projeto projeto1 = new Projeto("Projeto 1", "Descrição 1", Status.INICIADO, new ArrayList<>());
		Projeto projeto2 = new Projeto("Projeto 2", "Descrição 2", Status.CONCLUIDO, new ArrayList<>());
		List<Projeto> projetos = List.of(projeto1, projeto2);

		when(projetoService.listarProjetos()).thenReturn(projetos);

		mockMvc.perform(get("/projetos").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].titulo").value("Projeto 1"))
				.andExpect(jsonPath("$[1].titulo").value("Projeto 2"));
	}

	@Test
	public void testBuscarPorId_DeveRetornarProjetoPorId() throws Exception {
		Projeto projeto = new Projeto("Projeto 1", "Descrição 1", Status.INICIADO, new ArrayList<>());
		projeto.setId(1L);

		when(projetoService.buscarPorId(1L)).thenReturn(projeto);

		mockMvc.perform(get("/projetos/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.titulo").value("Projeto 1"))
				.andExpect(jsonPath("$.descricao").value("Descrição 1"));
	}

	@Test
	public void testAtualizarProjeto_DeveRetornarProjetoAtualizado() throws Exception {
		ProjetoDTO projetoDTO = new ProjetoDTO();
		projetoDTO.setTitulo("Projeto Atualizado");
		projetoDTO.setDescricao("Descrição Atualizada");
		projetoDTO.setStatus(Status.INICIADO);

		Projeto projeto = new Projeto("Projeto Atualizado", "Descrição Atualizada", Status.INICIADO, new ArrayList<>());

		when(projetoService.atualizarProjeto(eq(1L), any(Projeto.class))).thenReturn(projeto);

		mockMvc.perform(put("/projetos/1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(projetoDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.titulo").value("Projeto Atualizado"))
				.andExpect(jsonPath("$.descricao").value("Descrição Atualizada"));
	}
}
