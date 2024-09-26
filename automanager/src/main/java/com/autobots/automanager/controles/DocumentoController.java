package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {
	@Autowired
	private DocumentoService documentoServico;

	@GetMapping("/{id}")
	public ResponseEntity<Documento> getDocumento(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
		Documento documento = documentoServico.findById(id);
		return ResponseEntity.ok(documento);
	}

	@PostMapping
	public ResponseEntity<Documento> createDocumento(@RequestBody Documento documento) {
		Documento savedDocumento = documentoServico.save(documento);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedDocumento);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
		documentoServico.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Documento>> getAllDocumentos() {
		List<Documento> documentos = documentoServico.findAll();
		return ResponseEntity.ok(documentos);
	}
}
