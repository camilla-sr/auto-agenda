package br.com.autoagenda.autoagenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.autoagenda.autoagenda.model.M_Peca;

@Controller
@RequestMapping("/pecas")
public class C_Peca {
	
	@PostMapping("/cadastrarPeca")
    public String cadastrarPeca(@RequestParam String peca) {
        if(peca == null || peca.trim().isEmpty()) return "campo-vazio";
        
        M_Peca peca = new M_Peca();
        boolean resposta = peca.cadastrarPeca(peca);
        if(!resposta) return "cadastro-nao-concluido";
        
        return "peca-cadastrada";
    }

    public void editarPeca() {
        
    }

//    public void consPeca() {
//        pc.listarPecas();
//    }

    public void apagarPeca() {
    }

}
