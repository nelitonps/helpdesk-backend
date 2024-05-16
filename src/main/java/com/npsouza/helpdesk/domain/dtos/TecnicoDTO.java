package com.npsouza.helpdesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.npsouza.helpdesk.domain.Tecnico;
import com.npsouza.helpdesk.domain.enums.Perfil;

import jakarta.validation.constraints.NotNull;

public class TecnicoDTO implements Serializable{
	
	//Serializable é usado para que seja criado uma sequencia de Bits das instâncias dessa classe, para que possam ser trafegados em rede 
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	@NotNull(message = "O campo NOME é obrigatório")
	protected String nome;
	@NotNull(message = "O campo CPF é obrigatório")
	protected String cpf;
	@NotNull(message = "O campo EMAIL é obrigatório")
	protected String email;
	@NotNull(message = "O campo SENHA é obrigatório")
	protected String senha;
	protected Set<Integer> perfis = new HashSet<>(); //Set não permite ter dois valores iguais dentro da lista, iniciando com valor HashSet para evitar a questão do ponteiro nulo nullpointerexception
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriação = LocalDate.now();

	public TecnicoDTO() {
		super();
		addPerfis(Perfil.CLIENTE);
	}

	public TecnicoDTO(Tecnico obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
		this.dataCriação = obj.getDataCriação();
		addPerfis(Perfil.CLIENTE);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfis(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	public LocalDate getDataCriação() {
		return dataCriação;
	}

	public void setDataCriação(LocalDate dataCriação) {
		this.dataCriação = dataCriação;
	}

}
