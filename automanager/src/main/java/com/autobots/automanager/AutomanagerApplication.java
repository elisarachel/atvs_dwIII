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

import java.util.Optional;

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

		Optional<Usuario> adminUser = repositorio.findByNomeUsuario("admin");
		if (adminUser.isEmpty()) {
			Usuario admin = new Usuario();
			admin.setNome("Administrador");
			admin.getPerfis().add(Perfil.ROLE_ADMIN);
			CredencialUsuarioSenha credencialAdmin = new CredencialUsuarioSenha();
			credencialAdmin.setNomeUsuario("admin");
			credencialAdmin.setSenha(codificador.encode("admin123"));
			admin.getCredenciais().add(credencialAdmin);
			repositorio.save(admin);
		}

		Optional<Usuario> clienteUser = repositorio.findByNomeUsuario("cliente");
		if (clienteUser.isEmpty()) {
			Usuario cliente = new Usuario();
			cliente.setNome("Cliente");
			cliente.getPerfis().add(Perfil.ROLE_CLIENTE);
			CredencialUsuarioSenha credencialCliente = new CredencialUsuarioSenha();
			credencialCliente.setNomeUsuario("cliente");
			credencialCliente.setSenha(codificador.encode("cliente123"));
			cliente.getCredenciais().add(credencialCliente);
			repositorio.save(cliente);
		}

		Optional<Usuario> gerenteUser = repositorio.findByNomeUsuario("gerente");
		if (gerenteUser.isEmpty()) {
			Usuario gerente = new Usuario();
			gerente.setNome("Gerente");
			gerente.getPerfis().add(Perfil.ROLE_GERENTE);
			CredencialUsuarioSenha credencialGerente = new CredencialUsuarioSenha();
			credencialGerente.setNomeUsuario("gerente");
			credencialGerente.setSenha(codificador.encode("gerente123"));
			gerente.getCredenciais().add(credencialGerente);
			repositorio.save(gerente);
		}

		Optional<Usuario> vendedorUser = repositorio.findByNomeUsuario("vendedor");
		if (vendedorUser.isEmpty()) {
			Usuario vendedor = new Usuario();
			vendedor.setNome("Vendedor");
			vendedor.getPerfis().add(Perfil.ROLE_VENDEDOR);
			CredencialUsuarioSenha credencialVendedor = new CredencialUsuarioSenha();
			credencialVendedor.setNomeUsuario("vendedor");
			credencialVendedor.setSenha(codificador.encode("vendedor123"));
			vendedor.getCredenciais().add(credencialVendedor);
			repositorio.save(vendedor);
		}

	}
}