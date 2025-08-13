package br.com.autoagenda.autoagenda.repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.autoagenda.autoagenda.model.Agendamento;

public interface AgendamentoRepository extends CrudRepository<Agendamento, Integer>{
	Iterable<Agendamento> findAll();
	
	@Query("select count(statusAgendamento) from Agendamento where statusAgendamento = 'agendado'")
    long agpendentes();
	
	@Query("select count(statusAgendamento) from Agendamento where statusAgendamento = 'em_andamento'")
    long agandamento();
	
	@Query("select count(statusAgendamento) from Agendamento where statusAgendamento = 'concluido'")
    long agconcluidos();
	
	@Query("select count(statusAgendamento) from Agendamento where statusAgendamento = 'concluido'")
    long agconcluidohoje();
}