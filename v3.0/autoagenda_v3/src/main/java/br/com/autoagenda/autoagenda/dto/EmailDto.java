package br.com.autoagenda.autoagenda.dto;

public class EmailDto {
	private String destinatario;
	private String assunto;
	private String texto;

	public String getDestinatario() { return destinatario; }
	public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
	public String getAssunto() { return assunto; }
	public void setAssunto(String assunto) { this.assunto = assunto; }
	public String getTexto() { return texto; }
	public void setTexto(String texto) { this.texto = texto; }
}