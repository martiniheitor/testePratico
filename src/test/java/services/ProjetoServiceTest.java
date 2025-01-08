package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import entities.Projeto;
import enums.Status;
import jakarta.persistence.EntityNotFoundException;
import repositories.ProjetoRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProjetoServiceTest {

	@InjectMocks
	private ProjetoService projetoService;

	@Mock
	private ProjetoRepository projetoRepository;

	@Mock
	private TarefaService tarefaService;

	@Test
	public void testCriarProjeto() {
		Projeto projeto = new Projeto("Projeto 1", "Descrição 1", Status.INICIADO, new ArrayList<>());
		when(projetoRepository.save(any(Projeto.class))).thenReturn(projeto);

		Projeto result = projetoService.criarProjeto(projeto);

		assertNotNull(result);
		assertEquals("Projeto 1", result.getTitulo());

		verify(projetoRepository, times(1)).save(projeto);
	}

	@Test
	public void testCriarProjetoComTituloVazio() {
		Projeto projeto = new Projeto("", "Descrição 1", Status.INICIADO, new ArrayList<>());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			projetoService.criarProjeto(projeto);
		});
		assertEquals("O título do projeto não pode estar vazio.", exception.getMessage());

	}

	@Test
	public void testListarProjetos() {
		List<Projeto> projetos = List.of(new Projeto("Projeto 1", "Descrição 1", Status.INICIADO, new ArrayList<>()));
		when(projetoRepository.findAll()).thenReturn(projetos);

		List<Projeto> result = projetoService.listarProjetos();

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(projetoRepository).findAll();
	}

	@Test
	public void testBuscarPorId() {
		Projeto projeto = new Projeto("Projeto 1", "Descrição 1", Status.INICIADO, new ArrayList<>());
		when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

		Projeto result = projetoService.buscarPorId(1L);

		assertNotNull(result);
		assertEquals("Projeto 1", result.getTitulo());
		verify(projetoRepository).findById(1L);
	}

	@Test
	public void testBuscarPorIdComIdInexistente() {
		when(projetoRepository.findById(1L)).thenReturn(Optional.empty());

		Exception exception = assertThrows(EntityNotFoundException.class, () -> {
			projetoService.buscarPorId(1L);
		});

		assertEquals("Projeto não encontrado.", exception.getMessage());
	}

	@Test
	public void testAtualizarProjeto() {
		Projeto projetoExistente = new Projeto("Projeto 1", "Descrição 1", Status.INICIADO, new ArrayList<>());
		Projeto projetoAtualizado = new Projeto("Projeto 1 Atualizado", "Descrição Atualizada", Status.CONCLUIDO,
				new ArrayList<>());

		when(projetoRepository.findById(1L)).thenReturn(Optional.of(projetoExistente));
		when(projetoRepository.save(any(Projeto.class))).thenReturn(projetoAtualizado);

		Projeto result = projetoService.atualizarProjeto(1L, projetoAtualizado);

		assertNotNull(result);
		assertEquals("Projeto 1 Atualizado", result.getTitulo());
		verify(projetoRepository).save(projetoAtualizado);
	}

	@Test
	public void testAtualizarProjetoComIdInexistente() {
		Projeto projetoAtualizado = new Projeto("Projeto 1 Atualizado", "Descrição Atualizada", Status.CONCLUIDO,
				new ArrayList<>());

		when(projetoRepository.findById(1L)).thenReturn(Optional.empty());

		Exception exception = assertThrows(EntityNotFoundException.class, () -> {
			projetoService.atualizarProjeto(1L, projetoAtualizado);
		});

		assertEquals("Projeto não encontrado.", exception.getMessage());
	}
}
