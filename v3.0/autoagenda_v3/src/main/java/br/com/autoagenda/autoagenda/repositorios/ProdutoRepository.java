package br.com.autoagenda.autoagenda.repositorios;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer>{
	List<Produto> findByOficina(Oficina oficina);
	
	Optional<Produto> findById(Integer id);
	
	long countByOficina(Oficina oficina);
	
	long countByEstoqueAtualAndOficina(int valor, Oficina oficina);
	
	@Query("SELECT COUNT(p) FROM Produto p WHERE p.estoqueAtual <= p.estoqueMinimo AND p.estoqueAtual > 0 and p.oficina = :oficina")
	long countEstoqueBaixoByOficina(Oficina oficina);
	
	@Query("SELECT SUM(p.precoCusto) FROM Produto p where p.oficina = :oficina")
	 Float sumTotalPrecoCustoByOficina(Oficina oficina); 
}