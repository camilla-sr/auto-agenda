package com.autoagenda.auth_api.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.autoagenda.auth_api.model.Codigo2FA;

public interface Codigo2FARepository extends CrudRepository<Codigo2FA, Long>{
	Optional<Codigo2FA> findTopByEmailOrderByHoraGeracaoDesc(String email);
}