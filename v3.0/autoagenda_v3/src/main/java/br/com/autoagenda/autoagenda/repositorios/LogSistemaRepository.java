package br.com.autoagenda.autoagenda.repositorios;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import br.com.autoagenda.autoagenda.model.LogSistema;

public interface LogSistemaRepository extends CrudRepository<LogSistema, Long> {
    List<LogSistema> findAllByOrderByDataEventoDesc();
}