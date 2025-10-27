package com.ms_email.email.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ms_email.email.dtos.EmailDto;
import com.ms_email.email.models.Email;
import com.ms_email.email.services.EmailService;
import jakarta.validation.Valid;

@RestController
public class C_Email {
	@Autowired EmailService serv;

	@PostMapping("/enviarEmail")
	public ResponseEntity<Email> enviandoEmail(@RequestBody @Valid EmailDto dto){
		Email email = new Email();
		BeanUtils.copyProperties(dto, email);
		Email emailSalvo = serv.registrarParaEnvio(email);
		
		return new ResponseEntity<>(emailSalvo, HttpStatus.CREATED);
	}
}