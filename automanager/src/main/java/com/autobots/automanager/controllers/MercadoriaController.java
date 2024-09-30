package com.autobots.automanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.services.MercadoriaService;

@RestController
@RequestMapping("/mercadorias")
public class MercadoriaController {

	@Autowired
	private MercadoriaService mercadoriaService;

	@GetMapping
	public List<Mercadoria> listarTodas() {
		return mercadoriaService.listarTodas();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Mercadoria> buscarPorId(@PathVariable Long id) {
		Optional<Mercadoria> mercadoria = mercadoriaService.buscarPorId(id);
		return mercadoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mercadoria criarMercadoria(@RequestBody Mercadoria mercadoria) {
		return mercadoriaService.salvarMercadoria(mercadoria);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirMercadoria(@PathVariable Long id) {
		mercadoriaService.excluirMercadoria(id);
		return ResponseEntity.noContent().build();
	}
}
