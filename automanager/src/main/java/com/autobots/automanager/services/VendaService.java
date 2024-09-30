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

	public List<Venda> listarTodas() {
		return vendaRepositorio.findAll();
	}

	public Optional<Venda> buscarPorId(Long id) {
		return vendaRepositorio.findById(id);
	}

	public Venda salvarVenda(Venda venda) {
		return vendaRepositorio.save(venda);
	}

	public void excluirVenda(Long id) {
		vendaRepositorio.deleteById(id);
	}
}
