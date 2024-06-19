package com.npsouza.helpdesk.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npsouza.helpdesk.domain.Chamado;
import com.npsouza.helpdesk.domain.dtos.ChamadoDTO;
import com.npsouza.helpdesk.services.ChamadoService;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {
	
	@Autowired
	private ChamadoService chamadoService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> findByIdChamadoResource(@PathVariable Integer id){
		Chamado obj = chamadoService.findByIdChamadoService(id);
		return ResponseEntity.ok().body(new ChamadoDTO(obj));
	}
	
	@GetMapping
	public ResponseEntity<List<ChamadoDTO>> findAllChamadoResource(){
		List<Chamado> list = chamadoService.findAllChamadoService();
		List<ChamadoDTO> listDTO = list.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList()); //Converte a lista para DTO
		return ResponseEntity.ok().body(listDTO);
	}
	
}
