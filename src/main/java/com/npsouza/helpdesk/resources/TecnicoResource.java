package com.npsouza.helpdesk.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npsouza.helpdesk.domain.Tecnico;
import com.npsouza.helpdesk.domain.dtos.TecnicoDTO;
import com.npsouza.helpdesk.services.TecnicoService;

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
		List<Tecnico> list = tecService.findAllService();
		List<TecnicoDTO> listDTO = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
}
