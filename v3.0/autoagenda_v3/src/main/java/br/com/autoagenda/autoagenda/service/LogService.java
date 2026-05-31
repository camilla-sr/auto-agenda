package br.com.autoagenda.autoagenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;
import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.model.LogSistema;
import br.com.autoagenda.autoagenda.model.SuperAdmin;
import br.com.autoagenda.autoagenda.repositorios.LogSistemaRepository;

@Service
public class LogService {
    @Autowired private LogSistemaRepository repo;
    @Autowired private HttpSession session;

    public void registrar(String acao, String tipoAlvo, Integer alvoId, String descricao, boolean isMobile) {
        try {
            LogSistema log = new LogSistema();            
            SuperAdmin superAdmin = (SuperAdmin) session.getAttribute("superAdminLogado");
            Funcionario func = (Funcionario) session.getAttribute("usuarioLogado");

            if (superAdmin != null) {
                log.setTipoUsuario("SuperAdmin");
                log.setIdUsuario(superAdmin.getIdSuperadmin());
                log.setNomeUsuario(superAdmin.getUsuario());
                
                if (func != null && !acao.contains("Login")) {
                    descricao += " (Ação executada via SuperAdmin na conta de: " + func.getUsuario() + ")";
                }

            } else if (func != null) {
                String sufixoOrigem = isMobile ? " (Mobile)" : " (Web)";
                log.setTipoUsuario(func.getAcesso() + sufixoOrigem);
                log.setIdUsuario(func.getIdFuncionario());
                log.setNomeUsuario(func.getUsuario());
                log.setOficina(func.getOficina());
            } else {
                log.setTipoUsuario("Sistema");
            }
            log.setAcao(acao);
            log.setTipoAlvo(tipoAlvo);
            log.setAlvoId(alvoId);
            log.setDescricao(descricao);
            repo.save(log);
        } catch (Exception e) {
            System.err.println("[LOG FALHOU] " + e.getMessage());
        }
    }
}