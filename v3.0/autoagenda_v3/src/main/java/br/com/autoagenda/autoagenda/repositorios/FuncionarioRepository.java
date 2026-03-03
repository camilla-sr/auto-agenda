package br.com.autoagenda.autoagenda.repositorios;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.model.Oficina;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer>{
	Funcionario findByUsuario(String usuario);
	Funcionario findByCpf(String cpf);
	Optional<Funcionario> findByEmail(String email);
	List<Funcionario> findByOficina(Oficina oficina);	
	Optional<Funcionario> findById(Integer id);
	
	boolean existsByUsuarioAndOficina_IdOficina(String usuario, Integer idOficina);
	List<Funcionario> findByOficinaAndIdFuncionarioNot(Oficina oficina, Integer idExcluido);
}