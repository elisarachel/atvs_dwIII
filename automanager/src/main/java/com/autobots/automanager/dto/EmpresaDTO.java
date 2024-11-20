package com.autobots.automanager.dto;

import java.util.Set;

public class EmpresaDTO {
	private Long id;
	private String razaoSocial;
	private String nomeFantasia;
	private Set<UsuarioDTO> usuarios; // Relacione apenas o que for necessário

	// Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public Set<UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}
}
