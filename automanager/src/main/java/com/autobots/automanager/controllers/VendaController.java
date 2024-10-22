package com.autobots.automanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.servicos.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaController {

	@Autowired
	private VendaService vendaService;

	// Administrador e Gerente podem listar todas as vendas
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
	@GetMapping
	public ResponseEntity<List<Venda>> listarVendas() {
		List<Venda> vendas = vendaService.buscarTodasVendas();
		return ResponseEntity.ok(vendas);
	}

	// Vendedor pode listar suas próprias vendas
	@PreAuthorize("hasRole('VENDEDOR')")
	@GetMapping("/vendedor/{vendedorId}")
	public ResponseEntity<List<Venda>> listarVendasPorVendedor(@PathVariable Long vendedorId) {
		List<Venda> vendas = vendaService.buscarVendasPorVendedor(vendedorId);
		return ResponseEntity.ok(vendas);
	}

	// Cliente pode ver suas próprias vendas
	@PreAuthorize("hasRole('CLIENTE')")
	@GetMapping("/cliente/{clienteId}")
	public ResponseEntity<List<Venda>> listarVendasPorCliente(@PathVariable Long clienteId) {
		List<Venda> vendas = vendaService.buscarVendasPorCliente(clienteId);
		return ResponseEntity.ok(vendas);
	}

	// Administrador e Gerente podem criar uma nova venda
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
	@PostMapping
	public ResponseEntity<Venda> criarVenda(@RequestBody Venda venda) {
		Venda novaVenda = vendaService.salvarVenda(venda);
		return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
	}

	// Administrador e Gerente podem atualizar uma venda
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
	@PutMapping("/{id}")
	public ResponseEntity<Venda> atualizarVenda(@PathVariable Long id, @RequestBody Venda vendaAtualizada) {
		Optional<Venda> vendaOptional = vendaService.buscarVendaPorId(id);
		if (vendaOptional.isPresent()) {
			Venda venda = vendaOptional.get();
			venda.setCadastro(vendaAtualizada.getCadastro());
			venda.setCliente(vendaAtualizada.getCliente());
			venda.setFuncionario(vendaAtualizada.getFuncionario());
			venda.setMercadorias(vendaAtualizada.getMercadorias());
			venda.setServicos(vendaAtualizada.getServicos());
			venda.setVeiculo(vendaAtualizada.getVeiculo());
			vendaService.salvarVenda(venda);
			return ResponseEntity.ok(venda);
		}
		return ResponseEntity.notFound().build();
	}

	// Administrador e Gerente podem deletar uma venda
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarVenda(@PathVariable Long id) {
		vendaService.deletarVenda(id);
		return ResponseEntity.noContent().build();
	}
}
