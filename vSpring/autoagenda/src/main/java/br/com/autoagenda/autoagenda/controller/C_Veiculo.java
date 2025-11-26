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
import br.com.autoagenda.autoagenda.model.Veiculo;
import br.com.autoagenda.autoagenda.repositorios.ClienteRepository;
import br.com.autoagenda.autoagenda.repositorios.VeiculoRepository;

@Controller
@RequestMapping("veiculo-api")
@ResponseBody
public class C_Veiculo {
	@Autowired private VeiculoRepository repo;
	@Autowired private ClienteRepository repoCliente;
	
	@PostMapping("/salvar")
	public ResponseEntity<Veiculo> salvar(@RequestBody Veiculo veiculo) {
		if (veiculo.getCliente() == null || veiculo.getCliente().getIdCliente() == null) {
	        return ResponseEntity.badRequest().build();
	    }
		
		Cliente cl = repoCliente.findById(veiculo.getCliente().getIdCliente()).orElseThrow();
		Veiculo veiculoSalvo;
		
		if(veiculo.getIdVeiculo() != null) {
			Veiculo existe = repo.findById(veiculo.getIdVeiculo()).orElse(null);
			if(existe != null) {
				existe.setCliente(cl);
				existe.setModelo(veiculo.getModelo());
				existe.setMarca(veiculo.getMarca());
				existe.setPlaca(veiculo.getPlaca());
				veiculoSalvo = repo.save(existe);
			}else {
				veiculo.setCliente(cl);
				veiculoSalvo = repo.save(veiculo);
			}
		}else{
			veiculo.setCliente(cl);
			veiculoSalvo = repo.save(veiculo);
		}
		return ResponseEntity.ok(veiculoSalvo);
	}
	
	@PostMapping(value = "/apagar")
	public String apagar(@RequestParam Integer idVeiculo) {
		if(idVeiculo!= null) { repo.deleteById(idVeiculo); }
		return "redirect:/agendamento?apagar=true";
	}
}