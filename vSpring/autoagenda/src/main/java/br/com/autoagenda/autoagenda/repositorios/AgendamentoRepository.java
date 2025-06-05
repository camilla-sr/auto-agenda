package br.com.autoagenda.autoagenda.repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.autoagenda.autoagenda.model.Agendamento;

public interface AgendamentoRepository extends CrudRepository<Agendamento, Integer>{

	Iterable<Agendamento> findAll();
	

}
