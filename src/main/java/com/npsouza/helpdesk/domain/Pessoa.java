package com.npsouza.helpdesk.domain;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.npsouza.helpdesk.domain.enums.Perfil;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public abstract class Pessoa implements Serializable{
	
	//Serializable é usado para que seja criado uma sequencia de Bits das instâncias dessa classe, para que possam ser trafegados em rede 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Para o banco de dados gerar um Id diferente para cada objeto
	protected Integer id;
	protected String nome;
	
	@Column(unique = true) //Valor unico no banco
	protected String cpf;
	
	@Column(unique = true)
	protected String email;
	protected String senha;
	
	@ElementCollection(fetch = FetchType.EAGER) //Informando que é uma coleção e quando eu der um GET e buscar esse usuário no banco essa lista de perfis tera que vir imediatamente com esse usuário para evitar problemas
	@CollectionTable(name = "Perfis")
	protected Set<Integer> perfis = new HashSet<>(); //Set não permite ter dois valores iguais dentro da lista, iniciando com valor HashSet para evitar a questão do ponteiro nulo nullpointerexception
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriação = LocalDate.now();
	
	public Pessoa() {
		super();
		//Para que todo usuario criado ja vai ter o perfil de cliente
		addPerfil(Perfil.CLIENTE);
	}

	public Pessoa(Integer id, String nome, String cpf, String email, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		addPerfil(Perfil.CLIENTE);
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

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	public LocalDate getDataCriação() {
		return dataCriação;
	}

	public void setDataCriação(LocalDate dataCriação) {
		this.dataCriação = dataCriação;
	}

	///HashCode e Equals é usado para fazer comparação pelos valores do objetos especificados aqui no caso o cpf e id
	@Override
	public int hashCode() {
		return Objects.hash(cpf, id);
	}

	
	@Override
	public boolean equals(Object obj) { 
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(id, other.id);
	}
	
	
	
	
	

}
