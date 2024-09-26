package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	@Autowired
	private ClienteService clienteServico;

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Cliente>> getCliente(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
		Cliente cliente = clienteServico.findById(id);

		// Criação do modelo HATEOAS
		EntityModel<Cliente> model = EntityModel.of(cliente);

		// Adicionando links HATEOAS
		model.add(linkTo(methodOn(ClienteController.class).getCliente(id)).withSelfRel());
		model.add(linkTo(methodOn(ClienteController.class).getAllClientes()).withRel("clientes"));
		model.add(linkTo(methodOn(ClienteController.class).updateCliente(id, cliente)).withRel("atualizar"));
		model.add(linkTo(methodOn(ClienteController.class).deleteCliente(id)).withRel("deletar"));

		return ResponseEntity.ok(model);
	}

	@PostMapping
	public ResponseEntity<EntityModel<Cliente>> createCliente(@RequestBody Cliente cliente) throws ChangeSetPersister.NotFoundException {
		Cliente savedCliente = clienteServico.save(cliente);

		// Criação do modelo HATEOAS
		EntityModel<Cliente> model = EntityModel.of(savedCliente);
		model.add(linkTo(methodOn(ClienteController.class).getCliente(savedCliente.getId())).withSelfRel());
		model.add(linkTo(methodOn(ClienteController.class).getAllClientes()).withRel("clientes"));

		return ResponseEntity.status(HttpStatus.CREATED).body(model);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
		clienteServico.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Cliente>> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) throws ChangeSetPersister.NotFoundException {
		Cliente updatedCliente = clienteServico.updateCliente(id, cliente);

		// Criação do modelo HATEOAS
		EntityModel<Cliente> model = EntityModel.of(updatedCliente);
		model.add(linkTo(methodOn(ClienteController.class).getCliente(id)).withSelfRel());
		model.add(linkTo(methodOn(ClienteController.class).getAllClientes()).withRel("clientes"));

		return ResponseEntity.ok(model);
	}

	@GetMapping
	public ResponseEntity<List<EntityModel<Cliente>>> getAllClientes() {
		List<Cliente> clientes = clienteServico.findAll();

		// Transformando a lista de clientes em uma lista de EntityModel com links HATEOAS
		List<EntityModel<Cliente>> clientesModel = clientes.stream()
				.map(cliente -> {
					try {
						return EntityModel.of(cliente,
								linkTo(methodOn(ClienteController.class).getCliente(cliente.getId())).withSelfRel(),
								linkTo(methodOn(ClienteController.class).getAllClientes()).withRel("clientes")
						);
					} catch (ChangeSetPersister.NotFoundException e) {
						throw new RuntimeException(e);
					}
				})
				.collect(Collectors.toList());

		return ResponseEntity.ok(clientesModel);
	}
}
