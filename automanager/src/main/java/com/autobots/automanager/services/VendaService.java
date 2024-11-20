package com.autobots.automanager.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.VendaRepositorio;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendaService {

	@Autowired
	private VendaRepositorio vendaRepositorio;

	@Autowired
	private EmpresaRepositorio empresaRepositorio;

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
	@Transactional
	public Venda salvarVenda(Venda venda) {
		Empresa empresa = venda.getEmpresa();
		if (empresa != null && empresa.getId() != null) {
			empresa = empresaRepositorio.findById(empresa.getId()).orElseThrow(() -> new IllegalArgumentException("Empresa not found"));
			venda.setEmpresa(empresa);
		}
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

	public List<Venda> buscarVendasPorPeriodo(Long empresaId, Date dataInicio, Date dataFim) {
		return vendaRepositorio.findByEmpresaIdAndCadastroBetween(empresaId, dataInicio, dataFim);
	}

}
