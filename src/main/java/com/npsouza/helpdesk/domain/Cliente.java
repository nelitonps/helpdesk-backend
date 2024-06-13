package com.npsouza.helpdesk.domain;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.npsouza.helpdesk.domain.dtos.ClienteDTO;
import com.npsouza.helpdesk.domain.enums.Perfil;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente extends Pessoa{
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore //Para corrigir erro na chamada das requisições do tipo GET
	@OneToMany(mappedBy = "cliente")  //Um cliente para muitos chamados
	private List<Chamado> chamado = new ArrayList<>();

	public Cliente() {
		super();
		addPerfil(Perfil.CLIENTE); //Sempre que um cliente for criado ja vai ter o perfil de cliente
	}

	public Cliente(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		addPerfil(Perfil.CLIENTE); //Sempre que um cliente for criado ja vai ter o perfil de cliente
	}
	
	public Cliente(ClienteDTO obj) { //construtor do TecnicoDTO
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
		this.dataCriação = obj.getDataCriação();
	}

	public List<Chamado> getChamado() {
		return chamado;
	}

	public void setChamado(List<Chamado> chamado) {
		this.chamado = chamado;
	}
	
}
