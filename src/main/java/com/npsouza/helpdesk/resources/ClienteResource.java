package com.npsouza.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.npsouza.helpdesk.domain.Cliente;
import com.npsouza.helpdesk.domain.dtos.ClienteDTO;
import com.npsouza.helpdesk.services.ClienteService;

import jakarta.validation.Valid;

@RestController //Camada de Recursos
@RequestMapping(value = "/clientes") //Para adicionar o endpoint inicial para os servicos
public class ClienteResource {
	
	//localhost:8080/tecnicos/1
	
	@Autowired
	private ClienteService tecService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id){ //ResponseEntity é utilizado para representar, controlar toda resposta http
		Cliente obj = this.tecService.cliFindById(id);
		return ResponseEntity.ok().body(new ClienteDTO(obj));
	}
	
	//Endpoint que fara a listagem de todos os técnicos do banco
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll(){
		List<Cliente> list = tecService.findAllTecService();
		List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	//Endpoint que cria um novo técnico
	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO){ //@Valid é para validar os campos adicionados como notNull
		Cliente newObj = tecService.createTecService(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(null).build();
	}
	
	//Endpoint para atualizar as informações de um técnico
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO){
		Cliente obj = tecService.updateTecService(id, objDTO);
		return ResponseEntity.ok().body(new ClienteDTO(obj));
	}
	
	//Endpoint para deletar um técnico por id
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id){
		tecService.deleteTec(id);
		return ResponseEntity.noContent().build();
	}

	
}
