package com.npsouza.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.npsouza.helpdesk.domain.Cliente;
import com.npsouza.helpdesk.domain.Pessoa;
import com.npsouza.helpdesk.domain.dtos.ClienteDTO;
import com.npsouza.helpdesk.repositories.ClienteRepository;
import com.npsouza.helpdesk.repositories.PessoaRepository;
import com.npsouza.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.npsouza.helpdesk.services.exceptions.ObjectnotFoundException;

import jakarta.validation.Valid;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository cliRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Cliente cliFindById(Integer id) {
		Optional<Cliente> obj = cliRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado Id: " + id));
	}
	
	public List<Cliente> findAllTecService(){
		return cliRepository.findAll();
	}

	public Cliente createTecService(ClienteDTO objDTO) {
		
		objDTO.setId(null); //Por segurança para ter certeza que o id vai vir nulo
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfeEmail(objDTO);
		
		//Tem que ser salvo um tipo Cliente. Não salvar um TcnicoDTO pois ele não é uma entidade e não deve ser salvo no banco de dados 
		//Por isso foi adicionado um novo construtor do tipo de retorno ClienteDTO na classe Cliente para ser chamado aqui
		Cliente newObj = new Cliente(objDTO);
		return cliRepository.save(newObj);
	}
	
	public Cliente updateTecService(Integer id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente oldObj = cliFindById(id);
		validaPorCpfeEmail(objDTO);
		oldObj = new Cliente(objDTO);
		return cliRepository.save(oldObj);
	}
	
	public void deleteTec(Integer id) {
		Cliente obj = cliFindById(id);
		
		if(obj.getChamado().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possui ordens de servoço e não pode ser deletado!");
		}
		
		cliRepository.deleteById(id);
	}
	
	

	private void validaPorCpfeEmail(ClienteDTO objDTO) {
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
