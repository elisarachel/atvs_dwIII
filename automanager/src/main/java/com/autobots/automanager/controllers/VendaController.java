package com.autobots.automanager.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.autobots.automanager.converter.VendaConverter;
import com.autobots.automanager.dto.VendaDTO;
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

	private final VendaConverter vendaConverter = new VendaConverter();

	@GetMapping
	public List<VendaDTO> listarTodas() {
		return vendaService.listarTodas().stream()
				.map(vendaConverter::toDTO)
				.collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Venda> buscarPorId(@PathVariable Long id) {
		Optional<Venda> venda = vendaService.buscarPorId(id);
		return venda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Venda> atualizarVenda(@PathVariable Long id, @RequestBody Venda vendaDetails) {
		Optional<Venda> vendaOpt = vendaService.buscarPorId(id);
		if (vendaOpt.isPresent()) {
			Venda venda = vendaOpt.get();
			venda.setCadastro(vendaDetails.getCadastro());
			venda.setIdentificacao(vendaDetails.getIdentificacao());
			venda.setCliente(vendaDetails.getCliente());
			venda.setFuncionario(vendaDetails.getFuncionario());
			venda.setMercadorias(vendaDetails.getMercadorias());
			venda.setServicos(vendaDetails.getServicos());
			venda.setVeiculo(vendaDetails.getVeiculo());
			return ResponseEntity.ok(vendaService.salvarVenda(venda));
		} else {
			return ResponseEntity.notFound().build();
		}
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
