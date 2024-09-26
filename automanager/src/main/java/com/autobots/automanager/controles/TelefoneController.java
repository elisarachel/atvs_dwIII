package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.service.TelefoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefones")
public class TelefoneController {
	@Autowired
	private TelefoneService telefoneServico;

	@GetMapping("/{id}")
	public ResponseEntity<Telefone> getTelefone(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
		Telefone telefone = telefoneServico.findById(id);
		return ResponseEntity.ok(telefone);
	}

	@PostMapping
	public ResponseEntity<Telefone> createTelefone(@RequestBody Telefone telefone) {
		Telefone savedTelefone = telefoneServico.save(telefone);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedTelefone);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTelefone(@PathVariable Long id) {
		telefoneServico.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Telefone>> getAllTelefones() {
		List<Telefone> telefones = telefoneServico.findAll();
		return ResponseEntity.ok(telefones);
	}
}
