package br.com.autoagenda.autoagenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.autoagenda.autoagenda.model.Cliente;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.repositorios.ClienteRepository;

@Service
public class ClienteService {
    @Autowired private ClienteRepository repo;

    public Cliente salvarOuAtualizar(Cliente cl, Oficina oficina) {
        if (cl.getIdCliente() != null) {
            Cliente existe = repo.findById(cl.getIdCliente()).orElse(null);
            if (existe != null) {
                existe.setOficina(oficina);
                existe.setNomeCliente(cl.getNomeCliente());
                existe.setTelefone(cl.getTelefone());
                existe.setEmail(cl.getEmail());
                return repo.save(existe);
            }
        }
        cl.setOficina(oficina);
        return repo.save(cl);
    }

    public void inativar(Integer idCliente) {
        Cliente cl = repo.findById(idCliente).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
        cl.setAtivo(false);
        repo.save(cl);
    }
}