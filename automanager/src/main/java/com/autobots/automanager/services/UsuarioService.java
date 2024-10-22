package com.autobots.automanager.services;

import java.util.List;
import java.util.Optional;

import com.autobots.automanager.enumeracoes.Perfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	public List<Usuario> listarTodos() {
		return usuarioRepositorio.findAll();
	}

	public Optional<Usuario> buscarPorId(Long id) {
		return usuarioRepositorio.findById(id);
	}

	public Usuario salvarUsuario(Usuario usuario) {
		return usuarioRepositorio.save(usuario);
	}

	public void excluirUsuario(Long id) {
		usuarioRepositorio.deleteById(id);
	}

	public Optional<Usuario> buscarPorNomeUsuario(String nomeUsuario) {
		return usuarioRepositorio.findByNomeUsuario(nomeUsuario);
	}

	public List<Usuario> listarPorPerfil(Perfil perfil) {
		return usuarioRepositorio.findByPerfisContaining(perfil);
	}
}
