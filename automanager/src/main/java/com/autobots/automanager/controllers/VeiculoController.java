package com.autobots.automanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.services.VeiculoService;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

	@Autowired
	private VeiculoService veiculoService;

	@GetMapping
	public List<Veiculo> listarTodos() {
		return veiculoService.listarTodos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Veiculo> buscarPorId(@PathVariable Long id) {
		Optional<Veiculo> veiculo = veiculoService.buscarPorId(id);
		return veiculo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Veiculo> atualizarVeiculo(@PathVariable Long id, @RequestBody Veiculo veiculoDetails) {
		Optional<Veiculo> veiculoOpt = veiculoService.buscarPorId(id);
		if (veiculoOpt.isPresent()) {
			Veiculo veiculo = veiculoOpt.get();
			veiculo.setTipo(veiculoDetails.getTipo());
			veiculo.setModelo(veiculoDetails.getModelo());
			veiculo.setPlaca(veiculoDetails.getPlaca());
			veiculo.setProprietario(veiculoDetails.getProprietario());
			veiculo.setVendas(veiculoDetails.getVendas());
			return ResponseEntity.ok(veiculoService.salvarVeiculo(veiculo));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public Veiculo criarVeiculo(@RequestBody Veiculo veiculo) {
		return veiculoService.salvarVeiculo(veiculo);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirVeiculo(@PathVariable Long id) {
		veiculoService.excluirVeiculo(id);
		return ResponseEntity.noContent().build();
	}
}
