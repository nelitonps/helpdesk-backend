package com.npsouza.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.npsouza.helpdesk.domain.Pessoa;
import com.npsouza.helpdesk.domain.Tecnico;
import com.npsouza.helpdesk.domain.dtos.TecnicoDTO;
import com.npsouza.helpdesk.repositories.PessoaRepository;
import com.npsouza.helpdesk.repositories.TecnicoRepository;
import com.npsouza.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.npsouza.helpdesk.services.exceptions.ObjectnotFoundException;

import jakarta.validation.Valid;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Tecnico tecFindById(Integer id) {
		Optional<Tecnico> obj = tecRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado Id: " + id));
	}
	
	public List<Tecnico> findAllTecService(){
		return tecRepository.findAll();
	}

	public Tecnico createTecService(TecnicoDTO objDTO) {
		
		objDTO.setId(null); //Por segurança para ter certeza que o id vai vir nulo
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfeEmail(objDTO);
		
		//Tem que ser salvo um tipo Tecnico. Não salvar um TcnicoDTO pois ele não é uma entidade e não deve ser salvo no banco de dados 
		//Por isso foi adicionado um novo construtor do tipo de retorno TecnicoDTO na classe Tecnico para ser chamado aqui
		Tecnico newObj = new Tecnico(objDTO);
		return tecRepository.save(newObj);
	}
	
	public Tecnico updateTecService(Integer id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = tecFindById(id);
		validaPorCpfeEmail(objDTO);
		oldObj = new Tecnico(objDTO);
		return tecRepository.save(oldObj);
	}
	
	public void deleteTec(Integer id) {
		Tecnico obj = tecFindById(id);
		
		if(obj.getChamado().size() > 0) {
			throw new DataIntegrityViolationException("Tecnico possui ordens de servoço e não pode ser deletado!");
		}
		
		tecRepository.deleteById(id);
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
