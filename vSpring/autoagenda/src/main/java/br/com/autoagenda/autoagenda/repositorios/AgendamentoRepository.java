package br.com.autoagenda.autoagenda.repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import br.com.autoagenda.autoagenda.model.Agendamento;

public interface AgendamentoRepository extends CrudRepository<Agendamento, Integer>{
	Iterable<Agendamento> findAll();
	
	@Query("select count(*) from Agendamento where statusAgendamento = 'agendado'")
    long agpendentes();
	
	@Query("select count(*) from Agendamento where statusAgendamento = 'em_andamento'")
    long agandamento();
	
	@Query("select count(*) from Agendamento where statusAgendamento = 'concluido'")
    long agconcluidos();
	
	@Query("select count(*) from Agendamento where statusAgendamento = 'concluido' and dataConclusao is not null "
			+ "and dataConclusao = CURRENT_DATE")
    long agconcluidohoje();
}