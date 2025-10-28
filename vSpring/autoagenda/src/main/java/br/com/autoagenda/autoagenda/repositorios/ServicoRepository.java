package br.com.autoagenda.autoagenda.repositorios;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import br.com.autoagenda.autoagenda.model.Servico;

public interface ServicoRepository extends CrudRepository<Servico, Integer>{
	Iterable<Servico> findAll();
	Servico findByNomeServico(String servico);	
	Optional<Servico> findById(Integer id);
}