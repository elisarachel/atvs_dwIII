package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {
	@Autowired
	private DocumentoService documentoServico;

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Documento>> getDocumento(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
		Documento documento = documentoServico.findById(id);

		// Criando o modelo HATEOAS
		EntityModel<Documento> model = EntityModel.of(documento);
		model.add(linkTo(methodOn(DocumentoController.class).getDocumento(id)).withSelfRel());
		model.add(linkTo(methodOn(DocumentoController.class).getAllDocumentos()).withRel("documentos"));
		model.add(linkTo(methodOn(DocumentoController.class).deleteDocumento(id)).withRel("deletar"));

		return ResponseEntity.ok(model);
	}

	@PostMapping
	public ResponseEntity<EntityModel<Documento>> createDocumento(@RequestBody Documento documento) throws ChangeSetPersister.NotFoundException {
		Documento savedDocumento = documentoServico.save(documento);

		// Criando o modelo HATEOAS
		EntityModel<Documento> model = EntityModel.of(savedDocumento);
		model.add(linkTo(methodOn(DocumentoController.class).getDocumento(savedDocumento.getId())).withSelfRel());
		model.add(linkTo(methodOn(DocumentoController.class).getAllDocumentos()).withRel("documentos"));

		return ResponseEntity.status(HttpStatus.CREATED).body(model);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
		documentoServico.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<EntityModel<Documento>>> getAllDocumentos() {
		List<Documento> documentos = documentoServico.findAll();

		// Criando uma lista de EntityModel com links HATEOAS para cada documento
		List<EntityModel<Documento>> documentosModel = documentos.stream()
				.map(documento -> {
					try {
						return EntityModel.of(documento,
								linkTo(methodOn(DocumentoController.class).getDocumento(documento.getId())).withSelfRel(),
								linkTo(methodOn(DocumentoController.class).getAllDocumentos()).withRel("documentos")
						);
					} catch (ChangeSetPersister.NotFoundException e) {
						throw new RuntimeException(e);
					}
				})
				.collect(Collectors.toList());

		return ResponseEntity.ok(documentosModel);
	}
}
