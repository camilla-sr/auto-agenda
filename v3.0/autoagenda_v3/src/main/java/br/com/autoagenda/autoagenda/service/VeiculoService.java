package br.com.autoagenda.autoagenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.autoagenda.autoagenda.model.Cliente;
import br.com.autoagenda.autoagenda.model.Veiculo;
import br.com.autoagenda.autoagenda.repositorios.VeiculoRepository;

@Service
public class VeiculoService {
    @Autowired private VeiculoRepository repo;

    public Veiculo salvarOuAtualizar(Veiculo veiculo, Cliente cliente) {
        if (veiculo.getIdVeiculo() != null) {
            Veiculo existe = repo.findById(veiculo.getIdVeiculo()).orElse(null);
            if (existe != null) {
                existe.setCliente(cliente);
                existe.setModelo(veiculo.getModelo());
                existe.setMarca(veiculo.getMarca());
                existe.setPlaca(veiculo.getPlaca());
                return repo.save(existe);
            }
        }
        veiculo.setCliente(cliente);
        return repo.save(veiculo);
    }

    public void apagar(Integer idVeiculo) {
        if (idVeiculo != null) {
            repo.deleteById(idVeiculo);
        }
    }
}