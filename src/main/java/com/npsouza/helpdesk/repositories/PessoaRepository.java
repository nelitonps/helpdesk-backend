package com.npsouza.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npsouza.helpdesk.domain.Pessoa;


public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{ //Para fazer a persistencia das informações no banco

}
