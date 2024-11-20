package com.autobots.automanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.services.MercadoriaService;

@RestController
@RequestMapping("/mercadorias")
public class MercadoriaController {

	@Autowired
	private MercadoriaService mercadoriaService;

	// Administrador e Gerente podem listar todas as mercadorias
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
	@GetMapping
	public ResponseEntity<List<Mercadoria>> listarMercadorias() {
		List<Mercadoria> mercadorias = mercadoriaService.buscarTodasMercadorias();
		return ResponseEntity.ok(mercadorias);
	}

	// Administrador e Gerente podem criar uma nova mercadoria
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
	@PostMapping
	public ResponseEntity<Mercadoria> criarMercadoria(@RequestBody Mercadoria mercadoria) {
		Mercadoria novaMercadoria = mercadoriaService.salvarMercadoria(mercadoria);
		return ResponseEntity.status(HttpStatus.CREATED).body(novaMercadoria);
	}

	// Administrador e Gerente podem atualizar uma mercadoria
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
	@PutMapping("/{id}")
	public ResponseEntity<Mercadoria> atualizarMercadoria(@PathVariable Long id, @RequestBody Mercadoria mercadoriaAtualizada) {
		Optional<Mercadoria> mercadoriaOptional = mercadoriaService.buscarMercadoriaPorId(id);
		if (mercadoriaOptional.isPresent()) {
			Mercadoria mercadoria = mercadoriaOptional.get();
			mercadoria.setNome(mercadoriaAtualizada.getNome());
			mercadoria.setQuantidade(mercadoriaAtualizada.getQuantidade());
			mercadoria.setValor(mercadoriaAtualizada.getValor());
			mercadoriaService.salvarMercadoria(mercadoria);
			return ResponseEntity.ok(mercadoria);
		}
		return ResponseEntity.notFound().build();
	}

	// Administrador e Gerente podem deletar uma mercadoria
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarMercadoria(@PathVariable Long id) {
		mercadoriaService.deletarMercadoria(id);
		return ResponseEntity.noContent().build();
	}
}
