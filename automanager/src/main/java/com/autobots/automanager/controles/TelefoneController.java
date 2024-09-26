package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.service.TelefoneService;
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
@RequestMapping("/telefones")
public class TelefoneController {
	@Autowired
	private TelefoneService telefoneServico;

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Telefone>> getTelefone(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
		Telefone telefone = telefoneServico.findById(id);

		// Criando o modelo HATEOAS
		EntityModel<Telefone> model = EntityModel.of(telefone);
		model.add(linkTo(methodOn(TelefoneController.class).getTelefone(id)).withSelfRel());
		model.add(linkTo(methodOn(TelefoneController.class).getAllTelefones()).withRel("telefones"));
		model.add(linkTo(methodOn(TelefoneController.class).deleteTelefone(id)).withRel("deletar"));

		return ResponseEntity.ok(model);
	}

	@PostMapping
	public ResponseEntity<EntityModel<Telefone>> createTelefone(@RequestBody Telefone telefone) throws ChangeSetPersister.NotFoundException {
		Telefone savedTelefone = telefoneServico.save(telefone);

		// Criando o modelo HATEOAS
		EntityModel<Telefone> model = EntityModel.of(savedTelefone);
		model.add(linkTo(methodOn(TelefoneController.class).getTelefone(savedTelefone.getId())).withSelfRel());
		model.add(linkTo(methodOn(TelefoneController.class).getAllTelefones()).withRel("telefones"));

		return ResponseEntity.status(HttpStatus.CREATED).body(model);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTelefone(@PathVariable Long id) {
		telefoneServico.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<EntityModel<Telefone>>> getAllTelefones() {
		List<Telefone> telefones = telefoneServico.findAll();

		// Criando uma lista de EntityModel com links HATEOAS para cada telefone
		List<EntityModel<Telefone>> telefonesModel = telefones.stream()
				.map(telefone -> {
					try {
						return EntityModel.of(telefone,
								linkTo(methodOn(TelefoneController.class).getTelefone(telefone.getId())).withSelfRel(),
								linkTo(methodOn(TelefoneController.class).getAllTelefones()).withRel("telefones")
						);
					} catch (ChangeSetPersister.NotFoundException e) {
						throw new RuntimeException(e);
					}
				})
				.collect(Collectors.toList());

		return ResponseEntity.ok(telefonesModel);
	}
}
