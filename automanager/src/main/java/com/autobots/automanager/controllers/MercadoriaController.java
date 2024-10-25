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

	@PutMapping("/{id}")
	public ResponseEntity<Mercadoria> atualizarMercadoria(@PathVariable Long id, @RequestBody Mercadoria mercadoriaDetails) {
		Optional<Mercadoria> mercadoriaOpt = mercadoriaService.buscarPorId(id);
		if (mercadoriaOpt.isPresent()) {
			Mercadoria mercadoria = mercadoriaOpt.get();
			mercadoria.setNome(mercadoriaDetails.getNome());
			mercadoria.setDescricao(mercadoriaDetails.getDescricao());
			mercadoria.setValor(mercadoriaDetails.getValor());
			mercadoria.setValidade(mercadoriaDetails.getValidade());
			mercadoria.setFabricao(mercadoriaDetails.getFabricao());
			mercadoria.setCadastro(mercadoriaDetails.getCadastro());
			mercadoria.setQuantidade(mercadoriaDetails.getQuantidade());
			return ResponseEntity.ok(mercadoriaService.salvarMercadoria(mercadoria));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirMercadoria(@PathVariable Long id) {
		mercadoriaService.excluirMercadoria(id);
		return ResponseEntity.noContent().build();
	}
}
