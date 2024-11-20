package com.autobots.automanager.services;

import java.util.List;
import java.util.Optional;

import com.autobots.automanager.entidades.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import org.springframework.transaction.annotation.Transactional;
import com.autobots.automanager.repositorios.EmpresaRepositorio;

@Service
public class ServicoService {

	@Autowired
	private ServicoRepositorio servicoRepositorio;

	@Autowired
	private EmpresaRepositorio empresaRepositorio;

	// Buscar todos os serviços
	public List<Servico> buscarTodosServicos() {
		return servicoRepositorio.findAll();
	}

	// Salvar um novo serviço
	@Transactional
	public Servico salvarServico(Servico servico) {
		Empresa empresa = servico.getEmpresa();
		if (empresa != null && empresa.getId() != null) {
			empresa = empresaRepositorio.findById(empresa.getId()).orElseThrow(() -> new IllegalArgumentException("Empresa not found"));
			servico.setEmpresa(empresa);
		}
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

	public List<Servico> buscarServicosPorEmpresa(Long empresaId) {
		return servicoRepositorio.findByEmpresaId(empresaId);
	}
}
