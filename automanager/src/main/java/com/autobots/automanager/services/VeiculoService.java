package com.autobots.automanager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.repositorios.VeiculoRepositorio;

@Service
public class VeiculoService {

	@Autowired
	private VeiculoRepositorio veiculoRepositorio;

	public List<Veiculo> listarTodos() {
		return veiculoRepositorio.findAll();
	}

	public Optional<Veiculo> buscarPorId(Long id) {
		return veiculoRepositorio.findById(id);
	}

	public Veiculo salvarVeiculo(Veiculo veiculo) {
		return veiculoRepositorio.save(veiculo);
	}

	public void excluirVeiculo(Long id) {
		veiculoRepositorio.deleteById(id);
	}
}
