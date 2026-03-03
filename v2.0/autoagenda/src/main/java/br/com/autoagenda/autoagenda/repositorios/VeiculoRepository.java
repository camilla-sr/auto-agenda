package br.com.autoagenda.autoagenda.repositorios;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import br.com.autoagenda.autoagenda.model.Veiculo;

public interface VeiculoRepository extends CrudRepository<Veiculo, Integer>{
	List<Veiculo> findByCliente_IdCliente(Integer clienteId);
}