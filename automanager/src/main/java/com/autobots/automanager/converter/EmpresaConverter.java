package com.autobots.automanager.converter;

import com.autobots.automanager.dto.EmpresaDTO;
import com.autobots.automanager.dto.UsuarioDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;

import java.util.stream.Collectors;

public class EmpresaConverter {
	public EmpresaDTO toDTO(Empresa empresa) {
		EmpresaDTO dto = new EmpresaDTO();
		dto.setId(empresa.getId());
		dto.setRazaoSocial(empresa.getRazaoSocial());
		dto.setNomeFantasia(empresa.getNomeFantasia());

		if (empresa.getUsuarios() != null) {
			dto.setUsuarios(empresa.getUsuarios().stream()
					.map(this::toUsuarioDTO)
					.collect(Collectors.toSet()));
		}

		return dto;
	}

	private UsuarioDTO toUsuarioDTO(Usuario usuario) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(usuario.getId());
		dto.setNome(usuario.getNome());
		return dto;
	}
}
