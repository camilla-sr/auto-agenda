package br.com.autoagenda.autoagenda.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import br.com.autoagenda.autoagenda.model.Cliente;
import br.com.autoagenda.autoagenda.model.Veiculo;
import br.com.autoagenda.autoagenda.repositorios.ClienteRepository;
import br.com.autoagenda.autoagenda.repositorios.VeiculoRepository;

@Controller
@RequestMapping("/veiculo-api")
public class C_Veiculo {
    @Autowired private VeiculoRepository repo;
    @Autowired private ClienteRepository repoCliente;
    
    @GetMapping("/listar-por-cliente/{idCliente}")
    @ResponseBody
    public ResponseEntity<List<Veiculo>> listarPorCliente(@PathVariable Integer idCliente) {
        List<Veiculo> veiculos = repo.findByCliente_IdCliente(idCliente);
        return ResponseEntity.ok(veiculos);
    }

    @PostMapping("/salvar")
    @ResponseBody
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
            } else {
                veiculo.setCliente(cl);
                veiculoSalvo = repo.save(veiculo);
            }
        } else {
            veiculo.setCliente(cl);
            veiculoSalvo = repo.save(veiculo);
        }
        return ResponseEntity.ok(veiculoSalvo);
    }
    
    @PostMapping("/apagar")
    @ResponseBody
    public ResponseEntity<?> apagar(@RequestParam Integer idVeiculo) {
        try {
            if(idVeiculo != null) { 
                repo.deleteById(idVeiculo); 
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao excluir veículo");
        }
    }
}