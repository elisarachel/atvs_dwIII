package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
	@Autowired
	private EnderecoService enderecoServico;

	@GetMapping("/{id}")
	public ResponseEntity<Endereco> getEndereco(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
		Endereco endereco = enderecoServico.findById(id);
		return ResponseEntity.ok(endereco);
	}

	@PostMapping
	public ResponseEntity<Endereco> createEndereco(@RequestBody Endereco endereco) {
		Endereco savedEndereco = enderecoServico.save(endereco);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedEndereco);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
		enderecoServico.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Endereco>> getAllEnderecos() {
		List<Endereco> enderecos = enderecoServico.findAll();
		return ResponseEntity.ok(enderecos);
	}
}
