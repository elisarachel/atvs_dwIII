package com.autobots.automanager.service;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentoService {
	@Autowired
	private DocumentoRepositorio documentoRepositorio;

	public Documento findById(Long id) throws ChangeSetPersister.NotFoundException {
		return documentoRepositorio.findById(id)
				.orElseThrow(ChangeSetPersister.NotFoundException::new);
	}

	public Documento save(Documento documento) {
		return documentoRepositorio.save(documento);
	}

	public void deleteById(Long id) {
		documentoRepositorio.deleteById(id);
	}

	public List<Documento> findAll() {
		return documentoRepositorio.findAll();
	}
}
