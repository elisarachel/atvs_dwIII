package com.autobots.automanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.services.ServicoService;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

	@Autowired
	private ServicoService servicoService;

	// Administrador e Gerente podem listar todos os serviços
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
	@GetMapping
	public ResponseEntity<List<Servico>> listarServicos() {
		List<Servico> servicos = servicoService.buscarTodosServicos();
		return ResponseEntity.ok(servicos);
	}

	// Administrador e Gerente podem criar um novo serviço
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
	@PostMapping
	public ResponseEntity<Servico> criarServico(@RequestBody Servico servico) {
		Servico novoServico = servicoService.salvarServico(servico);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoServico);
	}

	// Administrador e Gerente podem atualizar um serviço
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
	@PutMapping("/{id}")
	public ResponseEntity<Servico> atualizarServico(@PathVariable Long id, @RequestBody Servico servicoAtualizado) {
		Optional<Servico> servicoOptional = servicoService.buscarServicoPorId(id);
		if (servicoOptional.isPresent()) {
			Servico servico = servicoOptional.get();
			servico.setNome(servicoAtualizado.getNome());
			servico.setDescricao(servicoAtualizado.getDescricao());
			servico.setValor(servicoAtualizado.getValor());
			servicoService.salvarServico(servico);
			return ResponseEntity.ok(servico);
		}
		return ResponseEntity.notFound().build();
	}

	// Administrador e Gerente podem deletar um serviço
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
		servicoService.deletarServico(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/empresa/{empresaId}")
	public ResponseEntity<List<Servico>> listarServicosPorEmpresa(@PathVariable Long empresaId) {
		List<Servico> servicos = servicoService.buscarServicosPorEmpresa(empresaId);
		return ResponseEntity.ok(servicos);
	}

}
