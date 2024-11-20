package com.autobots.automanager.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.autobots.automanager.converter.UsuarioConverter;
import com.autobots.automanager.dto.UsuarioDTO;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
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

	private final UsuarioConverter usuarioConverter = new UsuarioConverter();

	// LISTAR TODOS
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
	@GetMapping
	public List<UsuarioDTO> listarTodos() {
		Usuario usuarioAutenticado = usuarioService.getUsuarioAutenticado();
		return usuarioService.listarTodos().stream()
				.filter(usuario -> {
					if (usuarioAutenticado.getPerfis().contains(Perfil.ROLE_ADMIN)) {
						return true; // Admin can see all users
					} else if (usuarioAutenticado.getPerfis().contains(Perfil.ROLE_GERENTE)) {
						return usuario.getPerfis().contains(Perfil.ROLE_GERENTE) ||
								usuario.getPerfis().contains(Perfil.ROLE_VENDEDOR) ||
								usuario.getPerfis().contains(Perfil.ROLE_CLIENTE);
					} else if (usuarioAutenticado.getPerfis().contains(Perfil.ROLE_VENDEDOR)) {
						return usuario.getPerfis().contains(Perfil.ROLE_VENDEDOR) ||
								usuario.getPerfis().contains(Perfil.ROLE_CLIENTE);
					}
					return false;
				})
				.map(usuarioConverter::toDTO)
				.collect(Collectors.toList());
	}

	// BUSCAR POR ID
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'CLIENTE')")
	public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
		Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);

		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();

			// Validar permissões para acesso
			if (usuarioService.temPermissaoParaAlterar(usuario)) {
				return ResponseEntity.ok(usuario);
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}

		return ResponseEntity.notFound().build();
	}

	// CRIAR USUÁRIO
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
	public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
		// Garantir que o perfil a ser criado esteja dentro das permissões do usuário autenticado
		if (!usuarioService.temPermissaoParaAlterar(usuario)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
	}

	// ATUALIZAR USUÁRIO
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'CLIENTE')")
	public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario dadosAtualizados) {
		Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);

		if (usuarioOpt.isPresent()) {
			Usuario usuarioAtual = usuarioOpt.get();

			// Validar permissões para atualização
			if (!usuarioService.temPermissaoParaAlterar(usuarioAtual)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}

			// Atualizar os dados permitidos
			usuarioService.atualizarDadosPermitidos(usuarioAtual, dadosAtualizados);

			// Salvar alterações
			Usuario usuarioAtualizado = usuarioService.salvarUsuario(usuarioAtual);
			return ResponseEntity.ok(usuarioAtualizado);
		}

		return ResponseEntity.notFound().build();
	}

	// EXCLUIR USUÁRIO
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
	public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
		Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);

		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();

			// Validar permissões para exclusão
			if (!usuarioService.temPermissaoParaAlterar(usuario)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}

			usuarioService.excluirUsuario(id);
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();
	}
}

