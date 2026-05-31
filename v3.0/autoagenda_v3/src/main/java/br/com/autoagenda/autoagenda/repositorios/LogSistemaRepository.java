package br.com.autoagenda.autoagenda.repositorios;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.com.autoagenda.autoagenda.model.LogSistema;

public interface LogSistemaRepository extends JpaRepository<LogSistema, Long> {

	@Query("SELECT l FROM LogSistema l LEFT JOIN l.oficina o WHERE " +
	           "(:dataInicio IS NULL OR l.dataEvento >= :dataInicio) AND " +
	           "(:dataFim IS NULL OR l.dataEvento <= :dataFim) AND " +
	           "(:acao IS NULL OR :acao = '' OR l.acao = :acao) AND " +
	           "(:origem IS NULL OR :origem = '' OR l.tipoUsuario LIKE CONCAT('%', :origem, '%') OR (:origem = 'Web' AND l.tipoUsuario IN ('SuperAdmin', 'Sistema'))) AND " +
	           "(:idOficina IS NULL OR (:idOficina = 0 AND o IS NULL) OR (o.idOficina = :idOficina)) AND " +
	           "(:busca IS NULL OR :busca = '' OR " +
	           "LOWER(l.tipoUsuario) LIKE LOWER(CONCAT('%', :busca, '%')) OR " +
	           "LOWER(l.descricao) LIKE LOWER(CONCAT('%', :busca, '%')) OR " +
	           "LOWER(l.tipoAlvo) LIKE LOWER(CONCAT('%', :busca, '%')) OR " +
	           "LOWER(l.acao) LIKE LOWER(CONCAT('%', :busca, '%')) OR " +
	           "LOWER(CAST(l.idLog AS string)) LIKE LOWER(CONCAT('%', :busca, '%')) OR " +
	           "LOWER(CAST(l.idUsuario AS string)) LIKE LOWER(CONCAT('%', :busca, '%')) OR " +
	           "LOWER(CAST(l.alvoId AS string)) LIKE LOWER(CONCAT('%', :busca, '%')) OR " +
	           "LOWER(o.nomeFantasia) LIKE LOWER(CONCAT('%', :busca, '%')) ) " +
	           "ORDER BY l.dataEvento DESC")
	    Page<LogSistema> filtrarLogs(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim, 
	                                 @Param("acao") String acao, @Param("busca") String busca,
	                                 @Param("origem") String origem, @Param("idOficina") Integer idOficina, Pageable pageable);
}