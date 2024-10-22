package com.autobots.automanager;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.Perfil;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@SpringBootApplication
public class AutomanagerApplication implements CommandLineRunner {

	@Autowired
	private UsuarioRepositorio repositorio;

	public static void main(String[] args) {
		SpringApplication.run(AutomanagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();
		Usuario usuario = new Usuario();
		usuario.setNome("administrador");
		usuario.getPerfis().add(Perfil.ROLE_ADMIN);
		CredencialUsuarioSenha credencial = new CredencialUsuarioSenha();
		credencial.setNomeUsuario("admin");
		String senha  = "123456";
		credencial.setSenha(codificador.encode(senha));
		usuario.getCredenciais().add(credencial);
		repositorio.save(usuario);
	}
}