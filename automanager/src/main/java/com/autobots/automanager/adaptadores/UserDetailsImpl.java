package com.autobots.automanager.adaptadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.autobots.automanager.entidades.Credencial;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.Perfil;

public class UserDetailsImpl implements UserDetails {
	private Usuario usuario;

	public UserDetailsImpl(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> autoridades = new ArrayList<>();
		for (Perfil perfil : usuario.getPerfis()) {
			autoridades.add(new SimpleGrantedAuthority(perfil.name()));
		}
		return autoridades;
	}

	@Override
	public String getPassword() {
		// Encontrar a credencial de nome de usuário e senha entre as credenciais do usuário
		for (CredencialUsuarioSenha credencial : filtrarCredenciaisSenha()) {
			return credencial.getSenha();
		}
		return null;
	}

	@Override
	public String getUsername() {
		// Encontrar a credencial de nome de usuário e senha entre as credenciais do usuário
		for (CredencialUsuarioSenha credencial : filtrarCredenciaisSenha()) {
			return credencial.getNomeUsuario();
		}
		return null;
	}

	public Perfil getPerfil() {
		return usuario.getPerfis().iterator().next();
	}

	private List<CredencialUsuarioSenha> filtrarCredenciaisSenha() {
		List<CredencialUsuarioSenha> credenciaisUsuarioSenha = new ArrayList<>();
		for (CredencialUsuarioSenha credencial : usuario.getCredenciais()) {
			if (credencial != null) {
				credenciaisUsuarioSenha.add(credencial);
			}
		}
		return credenciaisUsuarioSenha;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
