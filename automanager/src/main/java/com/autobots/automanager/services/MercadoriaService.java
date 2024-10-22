package com.autobots.automanager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;

@Service
public class MercadoriaService {

	@Autowired
	private MercadoriaRepositorio mercadoriaRepositorio;

	// Buscar todas as mercadorias
	public List<Mercadoria> buscarTodasMercadorias() {
		return mercadoriaRepositorio.findAll();
	}

	// Salvar uma nova mercadoria
	public Mercadoria salvarMercadoria(Mercadoria mercadoria) {
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
}
