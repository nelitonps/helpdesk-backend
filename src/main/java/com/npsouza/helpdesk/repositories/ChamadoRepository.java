package com.npsouza.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npsouza.helpdesk.domain.Chamado;


public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{ //Para fazer a persistencia das informações no banco

}
