package com.npsouza.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npsouza.helpdesk.domain.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, Integer>{ //Para fazer a persistencia das informações no banco

}
