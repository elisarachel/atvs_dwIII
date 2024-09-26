package com.autobots.automanager.service;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {
	@Autowired
	private EnderecoRepositorio enderecoRepositorio;

	public Endereco findById(Long id) throws ChangeSetPersister.NotFoundException {
		return enderecoRepositorio.findById(id)
				.orElseThrow(ChangeSetPersister.NotFoundException::new);
	}

	public Endereco save(Endereco endereco) {
		return enderecoRepositorio.save(endereco);
	}

	public void deleteById(Long id) {
		enderecoRepositorio.deleteById(id);
	}

	public List<Endereco> findAll() {
		return enderecoRepositorio.findAll();
	}
}
