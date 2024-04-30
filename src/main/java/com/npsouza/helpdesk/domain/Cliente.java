package com.npsouza.helpdesk.domain;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	public List<Chamado> getChamado() {
		return chamado;
	}

	public void setChamado(List<Chamado> chamado) {
		this.chamado = chamado;
	}
	
}
