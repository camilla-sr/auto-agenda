package br.com.autoagenda.autoagenda.repositorios;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import br.com.autoagenda.autoagenda.model.Cliente;
import br.com.autoagenda.autoagenda.model.Oficina;

public interface ClienteRepository extends CrudRepository<Cliente, Integer>{
	List<Cliente> findByOficina(Oficina oficina);
	Optional<Cliente> findById(Integer id);
}