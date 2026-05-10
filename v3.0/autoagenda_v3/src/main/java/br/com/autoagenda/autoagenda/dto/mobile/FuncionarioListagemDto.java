package br.com.autoagenda.autoagenda.dto.mobile;

public record FuncionarioListagemDto(Integer idFuncionario, String nomeFuncionario, 
						String telefone, String usuario, String email, 
						String cpf, String acesso, boolean isAtivo, OficinaResumo oficina) {
	
    public record OficinaResumo(Integer idOficina) {}
}