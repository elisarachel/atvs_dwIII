package com.autobots.automanager.controllers;

import java.util.List;
import java.util.Optional;

import com.autobots.automanager.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.services.UsuarioService;
import com.autobots.automanager.enumeracoes.Perfil;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public List<Usuario> listarTodos() {
		return usuarioService.listarTodos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioService.buscarPorId(id);
		return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public Usuario criarUsuario(@RequestBody Usuario usuario) {
		return usuarioService.salvarUsuario(usuario);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
		usuarioService.excluirUsuario(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{usuarioId}/adicionar-credencial")
	public ResponseEntity<Usuario> adicionarCredencial(
			@PathVariable Long usuarioId,
			@RequestBody Credencial credencial) {

		Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(usuarioId);

		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();

			// Associando a nova credencial ao usuário
			usuario.getCredenciais().add(credencial);
			credencial.setInativo(false);  // Marcar credencial como ativa

			// Salvando as mudanças
			usuarioService.salvarUsuario(usuario);
			return ResponseEntity.ok(usuario);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/{usuarioId}/adicionar-perfil/{perfil}")
	public ResponseEntity<Usuario> adicionarPerfil(
			@PathVariable Long usuarioId,
			@PathVariable Perfil perfil) {

		Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(usuarioId);

		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();

			// Adicionando o perfil ao usuário
			usuario.getPerfis().add(perfil);

			// Salvando o usuário com o novo perfil
			usuarioService.salvarUsuario(usuario);

			return ResponseEntity.ok(usuario);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Autowired
	private UsuarioRepositorio repositorio;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("/cadastrar-cliente")
	public ResponseEntity<?> cadastrarCliente(@RequestBody Usuario cliente) {
		repositorio.save(cliente);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
	@GetMapping("/obter-clientes")
	public ResponseEntity<List<Usuario>> obterClientes() {
		return new ResponseEntity<>(repositorio.findAll(),HttpStatus.FOUND);
	}
}
