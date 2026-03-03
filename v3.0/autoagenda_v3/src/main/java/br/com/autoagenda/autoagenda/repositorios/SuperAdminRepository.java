package br.com.autoagenda.autoagenda.repositorios;

import org.springframework.data.repository.CrudRepository;
import br.com.autoagenda.autoagenda.model.SuperAdmin;

public interface SuperAdminRepository extends CrudRepository<SuperAdmin, Integer> {
	SuperAdmin findByUsuario(String usuario);
}