package br.com.autoagenda.autoagenda.repositorios;

import org.springframework.data.repository.CrudRepository;

import br.com.autoagenda.autoagenda.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer>{
	Iterable<Produto> findAll();
}
