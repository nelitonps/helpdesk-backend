package com.npsouza.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npsouza.helpdesk.domain.Tecnico;
import com.npsouza.helpdesk.repositories.TecnicoRepository;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecRepository;
	
	public Tecnico tecFindById(Integer id) {
		Optional<Tecnico> obj = tecRepository.findById(id);
		return obj.orElse(null);
	}
}
