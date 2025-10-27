package br.com.autoagenda.autoagenda.dto;

public class EmailDto {
	private String remetente;
	private String destinatario;
	private String assunto;
	private String texto;
	
	public EmailDto(String remetente, String destinatario, String assunto, String texto) {
		this.remetente = remetente;
		this.destinatario = destinatario;
		this.assunto = assunto;
		this.texto = texto;
	}

	public String getRemetente() { return remetente; }
	public void setRemetente(String remetente) { this.remetente = remetente; }
	public String getDestinatario() { return destinatario; }
	public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
	public String getAssunto() { return assunto; }
	public void setAssunto(String assunto) { this.assunto = assunto; }
	public String getTexto() { return texto; }
	public void setTexto(String texto) { this.texto = texto; }
}