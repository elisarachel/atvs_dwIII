package com.autobots.automanager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.VendaRepositorio;

@Service
public class VendaService {

	@Autowired
	private VendaRepositorio vendaRepositorio;

	// Buscar todas as vendas (administrador e gerente)
	public List<Venda> buscarTodasVendas() {
		return vendaRepositorio.findAll();
	}

	// Buscar vendas por vendedor (vendedor)
	public List<Venda> buscarVendasPorVendedor(Long vendedorId) {
		return vendaRepositorio.findByFuncionarioId(vendedorId);
	}

	// Buscar vendas por cliente (cliente)
	public List<Venda> buscarVendasPorCliente(Long clienteId) {
		return vendaRepositorio.findByClienteId(clienteId);
	}

	// Salvar uma nova venda (administrador e gerente)
	public Venda salvarVenda(Venda venda) {
		return vendaRepositorio.save(venda);
	}

	// Buscar venda por ID (para atualização ou deleção)
	public Optional<Venda> buscarVendaPorId(Long id) {
		return vendaRepositorio.findById(id);
	}

	// Deletar uma venda (administrador e gerente)
	public void deletarVenda(Long id) {
		vendaRepositorio.deleteById(id);
	}
}
