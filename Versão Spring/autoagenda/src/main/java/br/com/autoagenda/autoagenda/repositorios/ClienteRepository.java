package br.com.autoagenda.autoagenda.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autoagenda.autoagenda.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
