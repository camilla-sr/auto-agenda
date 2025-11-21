package br.com.autoagenda.autoagenda.repositorios;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import br.com.autoagenda.autoagenda.model.FotosAgendamento;

public interface FotosAgendamentoRepository extends CrudRepository<FotosAgendamento, Integer>{
	List<FotosAgendamento> findByAgendamentoId(Integer agendamentoId);
}