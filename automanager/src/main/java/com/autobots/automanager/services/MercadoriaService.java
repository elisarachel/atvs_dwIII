package com.autobots.automanager.services;

import java.util.List;
import java.util.Optional;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MercadoriaService {

	@Autowired
	private MercadoriaRepositorio mercadoriaRepositorio;

	@Autowired
	private EmpresaRepositorio empresaRepositorio;

	// Buscar todas as mercadorias
	public List<Mercadoria> buscarTodasMercadorias() {
		return mercadoriaRepositorio.findAll();
	}

	// Salvar uma nova mercadoria
	@Transactional
	public Mercadoria salvarMercadoria(Mercadoria mercadoria) {
		Empresa empresa = mercadoria.getEmpresa();
		if (empresa != null && empresa.getId() != null) {
			empresa = empresaRepositorio.findById(empresa.getId()).orElse(null);
			mercadoria.setEmpresa(empresa);
		}
		return mercadoriaRepositorio.save(mercadoria);
	}

	// Buscar mercadoria por ID (para atualização ou deleção)
	public Optional<Mercadoria> buscarMercadoriaPorId(Long id) {
		return mercadoriaRepositorio.findById(id);
	}

	// Deletar uma mercadoria
	public void deletarMercadoria(Long id) {
		mercadoriaRepositorio.deleteById(id);
	}

	public List<Mercadoria> buscarMercadoriasPorEmpresa(Long empresaId) {
		return mercadoriaRepositorio.findByEmpresaId(empresaId);
	}
}
