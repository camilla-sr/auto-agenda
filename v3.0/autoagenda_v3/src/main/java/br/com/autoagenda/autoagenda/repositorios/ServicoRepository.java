package br.com.autoagenda.autoagenda.repositorios;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.model.Servico;

public interface ServicoRepository extends CrudRepository<Servico, Integer>{
	List<Servico> findByOficina(Oficina oficina);
	Servico findByNomeServico(String servico);	
	Optional<Servico> findById(Integer id);
}