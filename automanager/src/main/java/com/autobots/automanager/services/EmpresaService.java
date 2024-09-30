package com.autobots.automanager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.repositorios.EmpresaRepositorio;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepositorio empresaRepositorio;

	public List<Empresa> listarTodas() {
		return empresaRepositorio.findAll();
	}

	public Optional<Empresa> buscarPorId(Long id) {
		return empresaRepositorio.findById(id);
	}

	public Empresa salvarEmpresa(Empresa empresa) {
		return empresaRepositorio.save(empresa);
	}

	public void excluirEmpresa(Long id) {
		empresaRepositorio.deleteById(id);
	}
}
