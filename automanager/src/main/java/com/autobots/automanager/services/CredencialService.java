package com.autobots.automanager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.repositorios.CredencialRepositorio;

@Service
public class CredencialService {

	@Autowired
	private CredencialRepositorio credencialRepositorio;

	public List<Credencial> listarTodas() {
		return credencialRepositorio.findAll();
	}

	public Optional<Credencial> buscarPorId(Long id) {
		return credencialRepositorio.findById(id);
	}

	public Credencial salvarCredencial(Credencial credencial) {
		return credencialRepositorio.save(credencial);
	}

	public void excluirCredencial(Long id) {
		credencialRepositorio.deleteById(id);
	}
}
