package br.com.autoagenda.autoagenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import br.com.autoagenda.autoagenda.model.Cliente;
import br.com.autoagenda.autoagenda.repositorios.ClienteRepository;

@Controller
@RequestMapping("/cliente-api")
public class C_Cliente {
	@Autowired private ClienteRepository repo;

	@PostMapping("/salvar")
	@ResponseBody
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cl) {
		Cliente clienteSalvo;
		
		if(cl.getIdCliente() != null) {
			Cliente existe = repo.findById(cl.getIdCliente()).orElse(null);
			if(existe != null) {
				existe.setNomeCliente(cl.getNomeCliente());
				existe.setTelefone(cl.getTelefone());
				existe.setEmail(cl.getEmail());
				clienteSalvo = repo.save(existe);	
			}else {
				clienteSalvo = repo.save(cl);
			}
		} else {
			clienteSalvo = repo.save(cl);
		}
		return ResponseEntity.ok(clienteSalvo);
    }

	@PostMapping("/apagar")
    public String apagarCliente(@RequestParam Integer idCliente) {
        if(idCliente != null) { 
            try {
                repo.deleteById(idCliente); 
            } catch (Exception e) {
                return "redirect:/clientes?erro=true";
            }
        }
        return "redirect:/clientes?sucesso=true";
    }
}