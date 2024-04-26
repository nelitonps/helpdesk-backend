package com.npsouza.helpdesk;

import java.util.Arrays;

import org.hibernate.dialect.function.array.ArraysOfSameTypeArgumentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.npsouza.helpdesk.domain.Chamado;
import com.npsouza.helpdesk.domain.Cliente;
import com.npsouza.helpdesk.domain.Tecnico;
import com.npsouza.helpdesk.domain.enums.Perfil;
import com.npsouza.helpdesk.domain.enums.Prioridade;
import com.npsouza.helpdesk.domain.enums.Status;
import com.npsouza.helpdesk.repositories.ChamadoRepository;
import com.npsouza.helpdesk.repositories.ClienteRepository;
import com.npsouza.helpdesk.repositories.TecnicoRepository;

@SpringBootApplication
public class HelpDeskApplication implements CommandLineRunner{
	
	//@Autowired é Injeção de dependencias, para o proprio Spring ficar responsável por cirar, gerenciar e destruir instancias das classes no tempo correto
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelpDeskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception { //Vai rodar sempre que startar a aplicação
		Tecnico tec1 = new Tecnico(null, "Neliton PS", "12456488847", "nelitonteste@gmail", "123");
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Julia Stylo", "12256456847", "juliateste@gmail", "123");
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 1", "Primeiro Chamado", tec1, cli1);
		
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}

}
