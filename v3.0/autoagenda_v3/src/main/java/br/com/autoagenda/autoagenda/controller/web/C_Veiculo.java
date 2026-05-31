package br.com.autoagenda.autoagenda.controller.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import br.com.autoagenda.autoagenda.model.Cliente;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.model.Veiculo;
import br.com.autoagenda.autoagenda.repositorios.ClienteRepository;
import br.com.autoagenda.autoagenda.repositorios.VeiculoRepository;
import br.com.autoagenda.autoagenda.service.LogService;

@Controller
@RequestMapping("/{slug}/veiculo-api")
public class C_Veiculo {
	@Autowired private LogService log;
    @Autowired private VeiculoRepository repo;
    @Autowired private ClienteRepository repoCliente;
    
    @GetMapping("/listar-por-cliente/{idCliente}") @ResponseBody
    public ResponseEntity<List<Veiculo>> listarPorCliente(@SessionAttribute("oficinaAtual") Oficina oficina, @PathVariable Integer idCliente) {
        List<Veiculo> veiculos = repo.findByCliente_OficinaAndAtivoTrue(oficina);
        return ResponseEntity.ok(veiculos);
    }

    @PostMapping("/salvar") @ResponseBody
    public ResponseEntity<Veiculo> salvar(@RequestBody Veiculo veiculo) {
        if (veiculo.getCliente() == null || veiculo.getCliente().getIdCliente() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        boolean novo = veiculo.getIdVeiculo() == null;
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
            } else {
                veiculo.setCliente(cl);
                veiculoSalvo = repo.save(veiculo);
            }
        } else {
            veiculo.setCliente(cl);
            veiculoSalvo = repo.save(veiculo);
        }
        log.registrar(novo ? "Criação" : "Edição", "Veículo", veiculoSalvo.getIdVeiculo(),
                "Placa: " + veiculoSalvo.getPlaca()+ " | Modelo: " + veiculoSalvo.getModelo(), false);
        return ResponseEntity.ok(veiculoSalvo);
    }
    
    @PostMapping("/apagar") @ResponseBody
    public ResponseEntity<?> apagar(@RequestParam Integer idVeiculo) {
        try {
            if(idVeiculo != null) repo.deleteById(idVeiculo);
            
            log.registrar("Exclusão", "Veículo",idVeiculo, "Veículo removido ID: " + idVeiculo, false);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao excluir veículo");
        }
    }
}