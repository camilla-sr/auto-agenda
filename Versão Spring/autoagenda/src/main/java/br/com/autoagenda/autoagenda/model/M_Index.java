package br.com.autoagenda.autoagenda.model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class M_Index {
	
	@RequestMapping
	public String inicio() {
		return "index";
	}
}
