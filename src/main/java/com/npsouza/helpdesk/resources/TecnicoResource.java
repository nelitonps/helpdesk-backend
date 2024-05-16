package com.npsouza.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.npsouza.helpdesk.domain.Tecnico;
import com.npsouza.helpdesk.domain.dtos.TecnicoDTO;
import com.npsouza.helpdesk.services.TecnicoService;

import jakarta.validation.Valid;

@RestController //Camada de Recursos
@RequestMapping(value = "/tecnicos") //Para adicionar o endpoint inicial para os servicos
public class TecnicoResource {
	
	//localhost:8080/tecnicos/1
	
	@Autowired
	private TecnicoService tecService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){ //ResponseEntity é utilizado para representar, controlar toda resposta http
		Tecnico obj = this.tecService.tecFindById(id);
		return ResponseEntity.ok().body(new TecnicoDTO(obj));
	}
	
	//Endpoint que fara a listagem de todos os técnicos do banco
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll(){
		List<Tecnico> list = tecService.findAllTecService();
		List<TecnicoDTO> listDTO = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	//Endpoint que cria um novo técnico
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO){ //@Valid é para validar os campos adicionados como notNull
		Tecnico newObj = tecService.createTecService(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(null).build();
	}
	
}
