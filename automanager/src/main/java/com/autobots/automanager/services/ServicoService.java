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

	// Buscar todos os serviços
	public List<Servico> buscarTodosServicos() {
		return servicoRepositorio.findAll();
	}

	// Salvar um novo serviço
	public Servico salvarServico(Servico servico) {
		return servicoRepositorio.save(servico);
	}

	// Buscar serviço por ID (para atualização ou deleção)
	public Optional<Servico> buscarServicoPorId(Long id) {
		return servicoRepositorio.findById(id);
	}

	// Deletar um serviço
	public void deletarServico(Long id) {
		servicoRepositorio.deleteById(id);
	}
}
