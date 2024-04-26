package com.npsouza.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;

import com.npsouza.helpdesk.domain.enums.Perfil;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Tecnico extends Pessoa{
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "tecnico")  //Um tecnico para muitos chamados
	private List<Chamado> chamado = new ArrayList<>();

	public Tecnico() {
		super();
		addPerfil(Perfil.TECNICO); //Sempre que um tecnico for criado ja vai ter o perfil de tecnico
	}

	public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		addPerfil(Perfil.TECNICO); //Sempre que um tecnico for criado ja vai ter o perfil de tecnico
	}

	public List<Chamado> getChamado() {
		return chamado;
	}

	public void setChamado(List<Chamado> chamado) {
		this.chamado = chamado;
	}
	
}
