package br.com.autoagenda.autoagenda.repositorios;

import org.springframework.data.repository.CrudRepository;

import br.com.autoagenda.autoagenda.model.Servico;

public interface ServicoRepository extends CrudRepository<Servico, Integer>{
	Iterable<Servico> findAll();
}
