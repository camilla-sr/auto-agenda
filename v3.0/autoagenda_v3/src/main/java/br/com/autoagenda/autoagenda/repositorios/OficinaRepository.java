package br.com.autoagenda.autoagenda.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import br.com.autoagenda.autoagenda.model.Oficina;

public interface OficinaRepository extends CrudRepository<Oficina, Integer>{
	@Override
    List<Oficina> findAll();
	
	Optional<Oficina> findBySlug(String slug);
	long countByAtivoTrue();
}