package controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.TarefaDTO;
import entities.Projeto;
import entities.Tarefa;
import enums.Status;
import services.TarefaService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProjetoController.class)
public class TarefaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private TarefaService tarefaService;

	@InjectMocks
	private TarefaController tarefaController;

	@Test
	public void testCriarTarefa_ComDadosValidos_DeveRetornarStatusOk() throws Exception {
		TarefaDTO tarefaDTO = new TarefaDTO();
		tarefaDTO.setTitulo("Tarefa 1");
		tarefaDTO.setDescricao("Descrição da Tarefa 1");
		tarefaDTO.setStatus(Status.INICIADO);

		Projeto projeto = new Projeto();
		projeto.setId(1L);

		Tarefa tarefa = new Tarefa("Tarefa 1", "Descrição da Tarefa 1", Status.INICIADO, projeto);

		when(tarefaService.criarTarefa(any(Tarefa.class))).thenReturn(tarefa);

		mockMvc.perform(post("/tarefas").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(tarefaDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.titulo").value("Tarefa 1"))
				.andExpect(jsonPath("$.descricao").value("Descrição da Tarefa 1"));
	}

	@Test
	public void testBuscarPorId_DeveRetornarTarefaPorId() throws Exception {
		Projeto projeto = new Projeto();
		projeto.setId(1L);

		Tarefa tarefa = new Tarefa("Tarefa 1", "Descrição da Tarefa 1", Status.INICIADO, projeto);
		tarefa.setId(1L);

		when(tarefaService.buscarPorId(1L)).thenReturn(tarefa);

		mockMvc.perform(get("/tarefas/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.titulo").value("Tarefa 1"))
				.andExpect(jsonPath("$.descricao").value("Descrição da Tarefa 1"));
	}

	@Test
	public void testAtualizarTarefa_DeveRetornarTarefaAtualizada() throws Exception {
		TarefaDTO tarefaDTO = new TarefaDTO();
		tarefaDTO.setTitulo("Tarefa Atualizada");
		tarefaDTO.setDescricao("Descrição Atualizada");
		tarefaDTO.setStatus(Status.CONCLUIDO);

		Projeto projeto = new Projeto();
		projeto.setId(1L);

		Tarefa tarefa = new Tarefa("Tarefa Atualizada", "Descrição Atualizada", Status.CONCLUIDO, projeto);

		when(tarefaService.atualizarTarefa(eq(1L), any(Tarefa.class))).thenReturn(tarefa);

		mockMvc.perform(put("/tarefas/1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(tarefaDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.titulo").value("Tarefa Atualizada"))
				.andExpect(jsonPath("$.descricao").value("Descrição Atualizada"));
	}
}
