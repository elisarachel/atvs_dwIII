package com.autobots.automanager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.repositorios.ServicoRepositorio;

@Service
public class ServicoService {

	@Autowired
	private ServicoRepositorio servicoRepositorio;

	public List<Servico> listarTodos() {
		return servicoRepositorio.findAll();
	}

	public Optional<Servico> buscarPorId(Long id) {
		return servicoRepositorio.findById(id);
	}

	public Servico salvarServico(Servico servico) {
		return servicoRepositorio.save(servico);
	}

	public void excluirServico(Long id) {
		servicoRepositorio.deleteById(id);
	}
}
