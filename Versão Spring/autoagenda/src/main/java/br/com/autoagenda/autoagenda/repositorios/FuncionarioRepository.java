package br.com.autoagenda.autoagenda.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autoagenda.autoagenda.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer>{

}
