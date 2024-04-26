package com.npsouza.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npsouza.helpdesk.domain.Tecnico;


public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{ //Para fazer a persistencia das informações no banco

}
