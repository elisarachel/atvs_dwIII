package com.autobots.automanager.service;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelefoneService {
	@Autowired
	private TelefoneRepositorio telefoneRepositorio;

	public Telefone findById(Long id) throws ChangeSetPersister.NotFoundException {
		return telefoneRepositorio.findById(id)
				.orElseThrow(ChangeSetPersister.NotFoundException::new);
	}

	public Telefone save(Telefone telefone) {
		return telefoneRepositorio.save(telefone);
	}

	public void deleteById(Long id) {
		telefoneRepositorio.deleteById(id);
	}

	public List<Telefone> findAll() {
		return telefoneRepositorio.findAll();
	}
}
