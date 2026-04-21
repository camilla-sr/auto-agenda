package br.com.autoagenda.autoagenda.controller.mobile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.autoagenda.autoagenda.model.Cliente;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.model.Veiculo;
import br.com.autoagenda.autoagenda.repositorios.ClienteRepository;
import br.com.autoagenda.autoagenda.repositorios.VeiculoRepository;
import br.com.autoagenda.autoagenda.service.VeiculoService;

@RestController
@RequestMapping("/mobile/veiculo-api")
public class C_VeiculoMobile {
    @Autowired private VeiculoRepository repo;
    @Autowired private ClienteRepository repoCliente;
    @Autowired private VeiculoService serv;

    @GetMapping("/listar-por-cliente/{idCliente}")
    public ResponseEntity<?> listarPorCliente(@PathVariable Integer idCliente, @RequestHeader("idOficina") Integer idOficina) {
        Optional<Cliente> cl = repoCliente.findById(idCliente);
        if (cl.isEmpty() || !cl.get().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Cliente não encontrado nesta oficina."));
        }
        Oficina oficina = cl.get().getOficina();
        List<Veiculo> veiculos = repo.findByCliente_OficinaAndAtivoTrue(oficina);
        return ResponseEntity.ok(veiculos);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Veiculo veiculo, @RequestHeader("idOficina") Integer idOficina) {
        if (veiculo.getCliente() == null || veiculo.getCliente().getIdCliente() == null) {
            return ResponseEntity.badRequest().body(Map.of("erro", "O veículo deve estar vinculado a um cliente."));
        }

        Optional<Cliente> cl = repoCliente.findById(veiculo.getCliente().getIdCliente());
        if (cl.isEmpty() || !cl.get().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("erro", "Cliente inválido ou pertence a outra oficina."));
        }
        Veiculo veiculoSalvo = serv.salvarOuAtualizar(veiculo, cl.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagar(@PathVariable Integer id, @RequestHeader("idOficina") Integer idOficina) {
        Optional<Veiculo> veiculo = repo.findById(id);
        if (veiculo.isEmpty() || !veiculo.get().getCliente().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Veículo não encontrado."));
        }
        
        try {
            serv.apagar(id);
            return ResponseEntity.ok(Map.of("mensagem", "Veículo excluído com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Erro ao excluir veículo (Pode estar vinculado a um agendamento)."));
        }
    }
}