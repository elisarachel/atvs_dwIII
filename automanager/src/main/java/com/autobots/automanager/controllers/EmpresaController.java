package com.autobots.automanager.controllers;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.services.EmpresaService;
import com.autobots.automanager.services.UsuarioService;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public List<Empresa> listarTodas() {
		return empresaService.listarTodas();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id) {
		Optional<Empresa> empresa = empresaService.buscarPorId(id);
		return empresa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public Empresa criarEmpresa(@RequestBody Empresa empresa) {
		return empresaService.salvarEmpresa(empresa);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirEmpresa(@PathVariable Long id) {
		empresaService.excluirEmpresa(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresaDetails) {
		Optional<Empresa> empresaOpt = empresaService.buscarPorId(id);
		if (empresaOpt.isPresent()) {
			Empresa empresa = empresaOpt.get();
			empresa.setNome(empresaDetails.getNome());
			empresa.setEndereco(empresaDetails.getEndereco());
			empresa.setTelefones(empresaDetails.getTelefones());
			empresa.setUsuarios(empresaDetails.getUsuarios());
			return ResponseEntity.ok(empresaService.salvarEmpresa(empresa));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/{empresaId}/associar-usuario/{usuarioId}")
	public ResponseEntity<Empresa> associarUsuario(
			@PathVariable Long empresaId,
			@PathVariable Long usuarioId) {

		Optional<Empresa> empresaOpt = empresaService.buscarPorId(empresaId);
		Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(usuarioId);

		if (empresaOpt.isPresent() && usuarioOpt.isPresent()) {
			Empresa empresa = empresaOpt.get();
			Usuario usuario = usuarioOpt.get();

			// Associando o usuário à empresa
			empresa.getUsuarios().add(usuario);
			usuario.setEmpresa(empresa);

			// Salvando as mudanças
			usuarioService.salvarUsuario(usuario);
			empresaService.salvarEmpresa(empresa);

			return ResponseEntity.ok(empresa);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
