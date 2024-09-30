package com.autobots.automanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.services.ServicoService;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

	@Autowired
	private ServicoService servicoService;

	@GetMapping
	public List<Servico> listarTodos() {
		return servicoService.listarTodos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Servico> buscarPorId(@PathVariable Long id) {
		Optional<Servico> servico = servicoService.buscarPorId(id);
		return servico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public Servico criarServico(@RequestBody Servico servico) {
		return servicoService.salvarServico(servico);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirServico(@PathVariable Long id) {
		servicoService.excluirServico(id);
		return ResponseEntity.noContent().build();
	}
}
