package br.com.autoagenda.autoagenda.repositorios;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import br.com.autoagenda.autoagenda.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer>{
	Iterable<Cliente> findAll();
	Optional<Cliente> findById(Integer id);
	Long deleteById(Long id);
	Optional<Cliente> findEmailById(Integer id);
}