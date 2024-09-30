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

	public List<Mercadoria> listarTodas() {
		return mercadoriaRepositorio.findAll();
	}

	public Optional<Mercadoria> buscarPorId(Long id) {
		return mercadoriaRepositorio.findById(id);
	}

	public Mercadoria salvarMercadoria(Mercadoria mercadoria) {
		return mercadoriaRepositorio.save(mercadoria);
	}

	public void excluirMercadoria(Long id) {
		mercadoriaRepositorio.deleteById(id);
	}
}
