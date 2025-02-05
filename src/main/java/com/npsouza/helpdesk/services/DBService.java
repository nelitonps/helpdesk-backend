package com.npsouza.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.npsouza.helpdesk.domain.Chamado;
import com.npsouza.helpdesk.domain.Cliente;
import com.npsouza.helpdesk.domain.Tecnico;
import com.npsouza.helpdesk.domain.enums.Perfil;
import com.npsouza.helpdesk.domain.enums.Prioridade;
import com.npsouza.helpdesk.domain.enums.Status;
import com.npsouza.helpdesk.repositories.ChamadoRepository;
import com.npsouza.helpdesk.repositories.ClienteRepository;
import com.npsouza.helpdesk.repositories.PessoaRepository;
import com.npsouza.helpdesk.repositories.TecnicoRepository;


@Service
public class DBService {
	
	    //@Autowired é Injeção de dependencias, para delegar a responsabilidade para o proprio Spring cirar, gerenciar e destruir instancias das classes no tempo correto
		@Autowired
		private TecnicoRepository tecnicoRepository;
		@Autowired
		private ClienteRepository clienteRepository;
		@Autowired
		private ChamadoRepository chamadoRepository;
		@Autowired
		private BCryptPasswordEncoder encoder;

	public void instanciaDB() {
		
		Tecnico tec1 = new Tecnico(null, "Pati Silveira", "12456488847", "Pati@gmail", encoder.encode("123"));
		tec1.addPerfil(Perfil.ADMIN);
		Tecnico tec2 = new Tecnico(null, "Setreo Oliver", "12456487547", "Setro@gmail", encoder.encode("123"));
		tec2.addPerfil(Perfil.ADMIN);
		Tecnico tec3 = new Tecnico(null, "Mariana Colto", "12856487547", "Mariana@gmail", encoder.encode("123"));
		tec3.addPerfil(Perfil.ADMIN);
		
		
		
		Cliente cli1 = new Cliente(null, "Julia Stylo", "12256456847", "juliateste@gmail", encoder.encode("123"));
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 1", "Primeiro Chamado", tec1, cli1);
		
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		tecnicoRepository.saveAll(Arrays.asList(tec2));
		tecnicoRepository.saveAll(Arrays.asList(tec3));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}
	
}
