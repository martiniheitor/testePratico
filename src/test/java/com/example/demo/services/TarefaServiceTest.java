package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.demo.entities.Projeto;
import com.example.demo.entities.Tarefa;
import com.example.demo.enums.Status;
import com.example.demo.repositories.TarefaRepository;

@RunWith(MockitoJUnitRunner.class)
public class TarefaServiceTest {

	@InjectMocks
	private TarefaService tarefaService;

	@Mock
	private TarefaRepository tarefaRepository;

	@Test
	public void testCriarTarefa_ComDadosValidos_DeveSalvarTarefa() {
		Projeto projeto = new Projeto();
		Tarefa tarefa = new Tarefa("Tarefa 1", "Descrição da tarefa", Status.INICIADO, projeto);

		when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

		Tarefa result = tarefaService.criarTarefa(tarefa);

		assertNotNull(result);
		assertEquals("Tarefa 1", result.getTitulo());
		verify(tarefaRepository).save(tarefa);
	}

	@Test
	public void testBuscarPorId_ComIdExistente_DeveRetornarTarefa() {
		Projeto projeto = new Projeto();
		Tarefa tarefa = new Tarefa("Tarefa 1", "Descrição da tarefa", Status.INICIADO, projeto);
		when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));

		Tarefa result = tarefaService.buscarPorId(1L);

		assertNotNull(result);
		assertEquals("Tarefa 1", result.getTitulo());
		verify(tarefaRepository).findById(1L);
	}

	@Test
	public void testBuscarPorId_ComIdInexistente_DeveLancarExcecao() {
		when(tarefaRepository.findById(anyLong())).thenReturn(Optional.empty());

		Exception exception = assertThrows(NoSuchElementException.class, () -> {
			tarefaService.buscarPorId(1L);
		});

		assertEquals("Tarefa com ID 1 não encontrada.", exception.getMessage());
	}

	@Test
	public void testAtualizarTarefa_ComDadosValidos_DeveAtualizarTarefa() {
		Projeto projeto = new Projeto();
		Tarefa tarefaExistente = new Tarefa("Tarefa 1", "Descrição da tarefa", Status.INICIADO, projeto);
		Tarefa tarefaAtualizada = new Tarefa("Tarefa 1 Atualizada", "Descrição atualizada", Status.CONCLUIDO, projeto);

		when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefaExistente));
		when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefaAtualizada);

		Tarefa result = tarefaService.atualizarTarefa(1L, tarefaAtualizada);

		assertNotNull(result);
		assertEquals("Tarefa 1 Atualizada", result.getTitulo());
		verify(tarefaRepository).save(tarefaAtualizada);
	}
}
