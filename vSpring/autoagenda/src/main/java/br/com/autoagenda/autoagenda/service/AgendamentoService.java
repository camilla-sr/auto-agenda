package br.com.autoagenda.autoagenda.service;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.com.autoagenda.autoagenda.model.Agendamento;
import br.com.autoagenda.autoagenda.repositorios.AgendamentoRepository;
import br.com.autoagenda.autoagenda.repositorios.ClienteRepository;
import br.com.autoagenda.autoagenda.repositorios.ServicoRepository;
import br.com.autoagenda.autoagenda.repositorios.VeiculoRepository;

@Service
public class AgendamentoService {
	@Autowired private AgendamentoRepository repo;
    @Autowired private ServicoRepository repoServ;
    @Autowired private ClienteRepository repoCl;
    @Autowired private VeiculoRepository repoVe;
    @Autowired private FotosAgendamentoService fotoService;
    @Autowired private EmailService mailServ;

    public void salvarAgendamento(Agendamento ag, Integer idServico, Integer idCliente, 
                                  Integer idVeiculo, MultipartFile[] fotos, String tokenMobile) {
        
        ag.setServico(repoServ.findById(idServico).orElseThrow());
        ag.setCliente(repoCl.findById(idCliente).orElseThrow());
        ag.setVeiculo(repoVe.findById(idVeiculo).orElseThrow());

        Agendamento agSalvo;

        if (ag.getIdAgendamento() != null) {
            Agendamento existente = repo.findById(ag.getIdAgendamento()).orElseThrow();
            existente.setCliente(ag.getCliente());
            existente.setVeiculo(ag.getVeiculo());
            existente.setServico(ag.getServico());
            existente.setDataPrevisao(ag.getDataPrevisao());
            existente.setStatusAgendamento(ag.getStatusAgendamento());
            existente.setDataConclusao(ag.getDataConclusao());
            existente.setObservacao(ag.getObservacao());
            
            agSalvo = repo.save(existente);
        } else {
            if ("concluido".equals(ag.getStatusAgendamento())) { ag.setDataConclusao(LocalDate.now()); }
            agSalvo = repo.save(ag);
        }

        processarFotos(agSalvo, fotos, tokenMobile);
    }

    public void excluirAgendamento(Integer idAgendamento) {
        if (idAgendamento != null) {
            Agendamento ag = repo.findById(idAgendamento).orElseThrow();
            fotoService.apagarFotosDoAgendamento(ag); 
            
            repo.delete(ag);
        }
    }

    public void atualizarStatus(Integer id, String status) {
        Agendamento ag = repo.findById(id).orElseThrow();
        ag.setStatusAgendamento(status);
        repo.save(ag);
    }

    public Agendamento concluirAgendamento(Integer id) {
        Agendamento ag = repo.findById(id).orElseThrow();
        ag.setStatusAgendamento("concluido");
        ag.setDataConclusao(LocalDate.now());
        mailServ.enviarEmailConclusao(ag);
        return repo.save(ag);
    }

    private void processarFotos(Agendamento ag, MultipartFile[] fotos, String tokenMobile) {
        if (fotos != null && fotos.length > 0) {
            for (MultipartFile foto : fotos) {
                if (foto != null && !foto.isEmpty()) {
                    fotoService.salvarFoto(ag, foto);
                }
            }
        }
        if (tokenMobile != null && !tokenMobile.isEmpty()) {
            fotoService.vincularFotosAoAgendamento(tokenMobile, ag);
        }
    }
}