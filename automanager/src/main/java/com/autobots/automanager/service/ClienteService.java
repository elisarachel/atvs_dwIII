package com.autobots.automanager.service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepositorio clienteRepositorio;

	public Cliente findById(Long id) throws ChangeSetPersister.NotFoundException {
		return clienteRepositorio.findById(id)
				.orElseThrow(ChangeSetPersister.NotFoundException::new);
	}

	public Cliente save(Cliente cliente) {
		return clienteRepositorio.save(cliente);
	}

	public void deleteById(Long id) {
		clienteRepositorio.deleteById(id);
	}

	public List<Cliente> findAll() {
		return clienteRepositorio.findAll();
	}

	public Cliente updateCliente(Long id, Cliente updatedCliente) throws ChangeSetPersister.NotFoundException {
		Cliente existingCliente = findById(id);
		existingCliente.setNome(updatedCliente.getNome());
		existingCliente.setNomeSocial(updatedCliente.getNomeSocial());
		existingCliente.setDataNascimento(updatedCliente.getDataNascimento());
		existingCliente.setDataCadastro(updatedCliente.getDataCadastro());
		existingCliente.setDocumentos(updatedCliente.getDocumentos());
		existingCliente.setEndereco(updatedCliente.getEndereco());
		existingCliente.setTelefones(updatedCliente.getTelefones());
		return clienteRepositorio.save(existingCliente);
	}
}
