package br.com.autoagenda.autoagenda.repositorios;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.autoagenda.autoagenda.model.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer>{
	Funcionario findByUsuario(String usuario);
	
	Iterable<Funcionario> findAll();
	
	Optional<Funcionario> findById(Integer id);
}