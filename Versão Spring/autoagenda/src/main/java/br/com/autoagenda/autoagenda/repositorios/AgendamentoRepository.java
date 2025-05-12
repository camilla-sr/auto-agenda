package br.com.autoagenda.autoagenda.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autoagenda.autoagenda.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer>{

}
