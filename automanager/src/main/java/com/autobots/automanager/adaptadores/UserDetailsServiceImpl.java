package com.autobots.automanager.adaptadores;

import java.util.ArrayList;
import java.util.List;

import com.autobots.automanager.entidades.Credencial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio repositorio;

	private Usuario obterPorNome(String nomeUsuario) {
		List<Usuario> usuarios = repositorio.findAll();
		Usuario selecionado = null;

		for (Usuario usuario : usuarios) {
			for (CredencialUsuarioSenha credencial : filtrarCredenciaisSenha(usuario)) {
				if (credencial.getNomeUsuario().equals(nomeUsuario)) {
					selecionado = usuario;
					break;
				}
			}
			if (selecionado != null) {
				break;
			}
		}
		return selecionado;
	}

	private List<CredencialUsuarioSenha> filtrarCredenciaisSenha(Usuario usuario) {
		List<CredencialUsuarioSenha> credenciaisUsuarioSenha = new ArrayList<>();
		for (CredencialUsuarioSenha credencial : usuario.getCredenciais()) {
			if (credencial != null) {
				credenciaisUsuarioSenha.add(credencial);
			}
		}
		return credenciaisUsuarioSenha;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario selecionado = this.obterPorNome(username);
		if (selecionado == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserDetailsImpl(selecionado);
	}
}
