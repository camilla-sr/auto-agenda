package com.ms_email.email.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ms_email.email.enums.StatusEnvio;
import com.ms_email.email.models.Email;

public interface EmailRepository extends JpaRepository<Email, Long>{	
	List<Email> findByStatusEnvioNotAndTentativasLessThan(StatusEnvio status, int tentativas);
}