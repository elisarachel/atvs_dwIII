package com.autobots.automanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.services.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaController {

	@Autowired
	private VendaService vendaService;

	@GetMapping
	public List<Venda> listarTodas() {
		return vendaService.listarTodas();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Venda> buscarPorId(@PathVariable Long id) {
		Optional<Venda> venda = vendaService.buscarPorId(id);
		return venda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public Venda criarVenda(@RequestBody Venda venda) {
		return vendaService.salvarVenda(venda);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirVenda(@PathVariable Long id) {
		vendaService.excluirVenda(id);
		return ResponseEntity.noContent().build();
	}
}