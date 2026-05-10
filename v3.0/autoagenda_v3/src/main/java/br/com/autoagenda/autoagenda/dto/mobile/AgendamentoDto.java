package br.com.autoagenda.autoagenda.dto.mobile;

import java.time.LocalDate;
import java.util.List;

public record AgendamentoDto(
        Integer idAgendamento,
        OficinaResumo oficina,
        ClienteResumo cliente,
        VeiculoResumo veiculo,
        FuncionarioResumo funcionario,
        List<ServicoResumo> servicos,
        LocalDate dataCadastro,
        LocalDate dataPrevisao,
        LocalDate dataConclusao,
        String statusAgendamento,
        String observacao,
        boolean ativo) {
	
    public record OficinaResumo(Integer idOficina) {}
    public record ClienteResumo(Integer idCliente, String nomeCliente, String telefone) {}
    public record VeiculoResumo(Integer idVeiculo, String placa, String modelo) {}
    public record FuncionarioResumo(Integer idFuncionario, String nomeFuncionario) {}
    
    public record ServicoResumo(Integer idServico, String descricao) {} 
}