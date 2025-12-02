package com.ms_email.email.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.ms_email.email.enums.StatusEnvio;
import com.ms_email.email.models.Email;
import com.ms_email.email.repositories.EmailRepository;
import jakarta.transaction.Transactional;

@Service
public class EmailService {
	@Autowired EmailRepository repo;
	@Autowired JavaMailSender emailSender;
	@Value("${spring.mail.username}") private String remetentePadrao;
	
	private static final int MAX_TENTATIVAS = 3;
	
	@Transactional
	public Email registrarParaEnvio(Email email) {
		email.setDataCriacao(LocalDateTime.now());
		email.setStatusEnvio(StatusEnvio.PENDENTE);
        email.setTentativas(0);
        
        if (email.getRemetente() == null || email.getRemetente().isEmpty()) { email.setRemetente(remetentePadrao); }
		return repo.save(email);
	}

	@Scheduled(fixedDelay = 60000)
    @Transactional
	public void processarFilaDeEmails() {
		List<Email> emails = repo.findByStatusEnvioNotAndTentativasLessThan(
			StatusEnvio.ENVIADO, MAX_TENTATIVAS
		);
		
		for(Email email : emails) {
			tentarEnviar(email);
		}
	}
	
	public void tentarEnviar(Email email) {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom(email.getRemetente());
			msg.setTo(email.getDestinatario());
			msg.setSubject(email.getAssunto());
			msg.setText(email.getTexto());
			
			emailSender.send(msg);
			email.setDataEnvio(LocalDateTime.now());
			email.setStatusEnvio(StatusEnvio.ENVIADO);
		}catch(MailException e) {
			email.setStatusEnvio(StatusEnvio.FALHA);
		}finally{
			email.setTentativas(email.getTentativas() + 1);
			repo.save(email);
		}
	}
}