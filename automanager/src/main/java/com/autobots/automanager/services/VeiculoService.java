package com.autobots.automanager.services;

import java.util.List;
import java.util.Optional;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VeiculoService {

	@Autowired
	private VeiculoRepositorio veiculoRepositorio;

	@Autowired
	private EmpresaRepositorio empresaRepositorio;

	public List<Veiculo> listarTodos() {
		return veiculoRepositorio.findAll();
	}

	public Optional<Veiculo> buscarPorId(Long id) {
		return veiculoRepositorio.findById(id);
	}

	@Transactional
	public Veiculo salvarVeiculo(Veiculo veiculo) {
		Empresa empresa = veiculo.getEmpresa();
		if (empresa != null && empresa.getId() != null) {
			empresa = empresaRepositorio.findById(empresa.getId()).orElseThrow(() -> new IllegalArgumentException("Empresa not found"));
			veiculo.setEmpresa(empresa);
		}
		return veiculoRepositorio.save(veiculo);
	}

	public void excluirVeiculo(Long id) {
		veiculoRepositorio.deleteById(id);
	}

	public List<Veiculo> buscarVeiculosPorEmpresa(Long empresaId) {
		return veiculoRepositorio.findByEmpresaId(empresaId);
	}

}
