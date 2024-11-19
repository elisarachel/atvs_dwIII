package com.autobots.automanager.converter;

import com.autobots.automanager.dto.UsuarioDTO;
import com.autobots.automanager.entidades.Usuario;

public class UsuarioConverter {
	public UsuarioDTO toDTO(Usuario usuario) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(usuario.getId());
		dto.setNome(usuario.getNome());
		return dto;
	}
}
