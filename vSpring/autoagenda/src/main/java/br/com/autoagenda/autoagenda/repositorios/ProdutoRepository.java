package br.com.autoagenda.autoagenda.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import br.com.autoagenda.autoagenda.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer>{
	Iterable<Produto> findAll();
	
	Optional<Produto> findById(Integer id);
	
	long countByEstoqueAtualLessThan(int nivelCondicao);
	
	 @Query("SELECT SUM(p.precoCusto) FROM Produto p")
	 Float sumTotalPrecoCusto(); 
}