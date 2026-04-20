package br.com.autoagenda.autoagenda.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.model.Agendamento;
import br.com.autoagenda.autoagenda.model.Oficina;

public interface AgendamentoRepository extends CrudRepository<Agendamento, Integer>{
	List<Agendamento> findByOficinaAndAtivoTrue(Oficina oficina);
	
	long countByOficinaAndAtivoTrue(Oficina oficina);
	long countByOficina(Oficina oficina);
	
	@Query("select count(*) from Agendamento a where a.statusAgendamento = 'Agendado' and a.oficina = :oficina")
    long countPendentesByOficina(Oficina oficina);
	
	@Query("select count(*) from Agendamento a where a.statusAgendamento = 'Em Andamento' and a.oficina = :oficina")
    long countAndamentoByOficina(Oficina oficina);
	
	@Query("select count(*) from Agendamento a where a.statusAgendamento = 'concluido' and a.oficina = :oficina")
    long countConcluidosbyOficina(Oficina oficina);
	
	@Query("select count(*) from Agendamento a where a.statusAgendamento = 'concluido' and a.dataConclusao is not null "
			+ "and a.dataConclusao = CURRENT_DATE and a.oficina = :oficina")
    long countConcluidosHojeByOficina(Oficina oficina);
	
	@Query("SELECT a FROM Agendamento a WHERE a.funcionario = :funcionario ORDER BY a.dataPrevisao DESC")
	List<Agendamento> findHistoricoDoFuncionario(@Param("funcionario") Funcionario funcionario);
}