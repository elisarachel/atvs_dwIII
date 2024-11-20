package com.autobots.automanager.services;

import com.autobots.automanager.adaptadores.UserDetailsImpl;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.autobots.automanager.jwt.ProvedorJwt;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ProvedorJwt provedorJwt;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepositorio.findByNomeUsuario(username)
		        .map(UserDetailsImpl::new)
		        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public String authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
	    return provedorJwt.proverJwt(userDetails.getUsername(), userDetails.getPerfil());
    }

    public Usuario register(Usuario usuario) {
        Optional<Usuario> existingUser = usuarioRepositorio.findByNomeUsuario(usuario.getNome());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Usuário já existe");
        }
        for (CredencialUsuarioSenha credencial : usuario.getCredenciais()) {
            credencial.setSenha(passwordEncoder.encode(credencial.getSenha()));
        }
        return usuarioRepositorio.save(usuario);
    }
}