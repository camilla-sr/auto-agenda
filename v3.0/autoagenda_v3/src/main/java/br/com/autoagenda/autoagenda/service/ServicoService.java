package br.com.autoagenda.autoagenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.model.Servico;
import br.com.autoagenda.autoagenda.repositorios.ServicoRepository;

@Service
public class ServicoService {
	@Autowired  private ServicoRepository repo;

    public void salvarOuAtualizar(Servico serv, Oficina oficina) {
        if (serv.getIdServico() != null) {
            Servico existe = repo.findById(serv.getIdServico()).orElseThrow();
            existe.setOficina(oficina);
            existe.setNomeServico(serv.getNomeServico());
            existe.setDescServico(serv.getDescServico());
            repo.save(existe);
        } else {
            serv.setOficina(oficina);
            repo.save(serv);
        }
    }

    public void inativar(Integer idServico) {
        Servico serv = repo.findById(idServico).orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado."));
        serv.setAtivo(false);
        repo.save(serv);
    }
}