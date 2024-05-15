package com.npsouza.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npsouza.helpdesk.domain.Tecnico;
import com.npsouza.helpdesk.repositories.TecnicoRepository;
import com.npsouza.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecRepository;
	
	public Tecnico tecFindById(Integer id) {
		Optional<Tecnico> obj = tecRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado Id: " + id));
	}
	
	public List<Tecnico> findAllService(){
		return tecRepository.findAll();
	}
}
