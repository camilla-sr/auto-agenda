package br.com.autoagenda.autoagenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indeController {
	
	@RequestMapping
	public String inicio() {
		return "index";
	}
}
