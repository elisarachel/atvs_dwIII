package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.service.EnderecoService;
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
@RequestMapping("/enderecos")
public class EnderecoController {
	@Autowired
	private EnderecoService enderecoServico;

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Endereco>> getEndereco(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
		Endereco endereco = enderecoServico.findById(id);

		// Criando o modelo HATEOAS
		EntityModel<Endereco> model = EntityModel.of(endereco);
		model.add(linkTo(methodOn(EnderecoController.class).getEndereco(id)).withSelfRel());
		model.add(linkTo(methodOn(EnderecoController.class).getAllEnderecos()).withRel("enderecos"));
		model.add(linkTo(methodOn(EnderecoController.class).deleteEndereco(id)).withRel("deletar"));

		return ResponseEntity.ok(model);
	}

	@PostMapping
	public ResponseEntity<EntityModel<Endereco>> createEndereco(@RequestBody Endereco endereco) throws ChangeSetPersister.NotFoundException {
		Endereco savedEndereco = enderecoServico.save(endereco);

		// Criando o modelo HATEOAS
		EntityModel<Endereco> model = EntityModel.of(savedEndereco);
		model.add(linkTo(methodOn(EnderecoController.class).getEndereco(savedEndereco.getId())).withSelfRel());
		model.add(linkTo(methodOn(EnderecoController.class).getAllEnderecos()).withRel("enderecos"));

		return ResponseEntity.status(HttpStatus.CREATED).body(model);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
		enderecoServico.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<EntityModel<Endereco>>> getAllEnderecos() {
		List<Endereco> enderecos = enderecoServico.findAll();

		// Criando uma lista de EntityModel com links HATEOAS para cada endereço
		List<EntityModel<Endereco>> enderecosModel = enderecos.stream()
				.map(endereco -> {
					try {
						return EntityModel.of(endereco,
								linkTo(methodOn(EnderecoController.class).getEndereco(endereco.getId())).withSelfRel(),
								linkTo(methodOn(EnderecoController.class).getAllEnderecos()).withRel("enderecos")
						);
					} catch (ChangeSetPersister.NotFoundException e) {
						throw new RuntimeException(e);
					}
				})
				.collect(Collectors.toList());

		return ResponseEntity.ok(enderecosModel);
	}
}
