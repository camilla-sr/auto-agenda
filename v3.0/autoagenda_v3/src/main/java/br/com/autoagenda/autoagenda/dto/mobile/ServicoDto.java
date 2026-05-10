package br.com.autoagenda.autoagenda.dto.mobile;

public record ServicoDto(Integer idServico, OficinaResumo oficina, String nomeServico,
						String descServico, boolean ativo) {

	public record OficinaResumo(Integer idOficina) {}
}