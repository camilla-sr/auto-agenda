package br.com.autoagenda.autoagenda.repositorios;

import org.springframework.data.repository.CrudRepository;

import br.com.autoagenda.autoagenda.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer>{
	Iterable<Cliente> findAll();
}
