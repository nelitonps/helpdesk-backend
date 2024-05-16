package com.npsouza.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npsouza.helpdesk.domain.Pessoa;
import com.npsouza.helpdesk.domain.Tecnico;
import com.npsouza.helpdesk.domain.dtos.TecnicoDTO;
import com.npsouza.helpdesk.repositories.PessoaRepository;
import com.npsouza.helpdesk.repositories.TecnicoRepository;
import com.npsouza.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.npsouza.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Tecnico tecFindById(Integer id) {
		Optional<Tecnico> obj = tecRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado Id: " + id));
	}
	
	public List<Tecnico> findAllTecService(){
		return tecRepository.findAll();
	}

	public Tecnico createTecService(TecnicoDTO objDTO) {
		
		objDTO.setId(null); //Por segurança para ter certeza que o id vai vir nulo
		
		validaPorCpfeEmail(objDTO);
		
		//Tem que ser salvo um tipo Tecnico. Não salvar um TcnicoDTO pois ele não é uma entidade e não deve ser salvo no banco de dados 
		//Por isso foi adicionado um novo construtor do tipo de retorno TecnicoDTO na classe Tecnico para ser chamado aqui
		Tecnico newObj = new Tecnico(objDTO);
		return tecRepository.save(newObj);
	}

	private void validaPorCpfeEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
		
		//Reaproveitando o mesmo objeto criado acima para cpf
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
		
	}
}
