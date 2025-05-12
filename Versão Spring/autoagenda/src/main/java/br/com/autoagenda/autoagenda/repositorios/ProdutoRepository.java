package br.com.autoagenda.autoagenda.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autoagenda.autoagenda.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

}
