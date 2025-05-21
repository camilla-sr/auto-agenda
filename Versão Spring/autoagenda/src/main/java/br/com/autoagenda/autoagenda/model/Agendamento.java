package br.com.autoagenda.autoagenda.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "agendamento")
public class Agendamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAgendamento;
    private String dataCadastro;
    private String dataPrevisao;
    private String dataConclusao;
    private String status;
    private String observacao;
    
    @OneToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "fk_servico")
    private Servico servico;

    public Agendamento() {}
    
    // Métodos Principais
    public boolean cadastrarAgendamento(String dataPrevisao, String status, String observacao) {
    	String hoje = LocalDate.now().toString();
    	
    	String sqlInserir = "INSERT INTO agendamento "
                + "(fk_cliente, fk_servico, data_cadastro, data_previsao_entrega, status_agendamento, observacao) "
                + "VALUES ("
                + cliente.getIdCliente() + ", "
                + servico.getIdServico() + ", '"
                + hoje + "', '"
                + dataPrevisao + "', '"
                + status + "', '"
                + observacao + "')";
        
        return true;
    }

    /*public boolean editarAgendamento(int idAgendamento, int cliente, int servico, int funcionario, String dataPrevisaoEntrega, String obs) {
        String sqlEdit = ""; // Inicializo a variável da query

        // Atualizar apenas o Cliente
        if (cliente != 0 && servico == 0 && funcionario == 0 && dataPrevisaoEntrega == null && obs == null) {
            sqlEdit = "UPDATE agendamento SET fk_cliente = " + cliente;
        }
        // Atualizar apenas o Serviço
        if (servico != 0 && cliente == 0 && funcionario == 0 && dataPrevisaoEntrega == null && obs == null) {
            sqlEdit = "UPDATE agendamento SET fk_servico = " + servico;
        }
        // Atualizar apenas o Funcionário
        if (funcionario != 0 && cliente == 0 && servico == 0 && dataPrevisaoEntrega == null && obs == null) {
            sqlEdit = "UPDATE agendamento SET fk_funcionario = " + funcionario;
        }
        // Atualizar apenas a Data de Previsão de Entrega
        if (dataPrevisaoEntrega != null && cliente == 0 && servico == 0 && funcionario == 0 && obs == null) {
            sqlEdit = "UPDATE agendamento SET data_previsao_entrega = '" + dataPrevisaoEntrega + "'";
        }
        // Atualizar apenas a Observação
        if (obs != null && cliente == 0 && servico == 0 && funcionario == 0 && dataPrevisaoEntrega == null) {
            sqlEdit = "UPDATE agendamento SET observacao = '" + obs + "'";
        }
        // Atualizar todos os campos
        if (cliente != 0 && servico != 0 && funcionario != 0 && dataPrevisaoEntrega != null && obs != null) {
            sqlEdit = "UPDATE agendamento SET fk_cliente = " + cliente + ", fk_servico = " + servico
                    + ", fk_funcionario = " + funcionario + ", data_previsao_entrega = '" + dataPrevisaoEntrega
                    + "', observacao = '" + obs + "'";
        }

        sqlEdit = sqlEdit + " WHERE id_agendamento = " + idAgendamento;
        boolean resposta = conn.executar(sqlEdit);
        if (resposta) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

    public void listarAgendamento() {
        String sqlConsulta = "SELECT a.id_agendamento, cl.nome_cliente, s.desc_servico, \n"
                + "f.nome_funcionario, a.data_cadastro, a.data_previsao_entrega, a.data_conclusao, a.status_agendamento, a.observacao\n"
                + "from agendamento a join cliente cl on cl.id_cliente = a.fk_cliente\n"
                + "join tipo_servico s on a.fk_servico = s.id_servico\n"
                + "join funcionario f on a.fk_funcionario = f.id_funcionario";
        System.out.println("---------------------------");
        ResultSet lista = conn.executarConsulta(sqlConsulta);

        try {
            while (lista.next()) {
                int id = lista.getInt("id_agendamento");
                String idFuncionario = lista.getString("f.nome_funcionario");
                String idServico = lista.getString("s.desc_servico");
                String dataCadastro = lista.getString("a.data_cadastro");
                String dataPrevisaoEntrega = lista.getString("a.data_previsao_entrega");
                String dataConclusaoServico = lista.getString("a.data_conclusao");
                String statusAgendamento = lista.getString("a.status_agendamento");
                String observacao = lista.getString("a.observacao");

                String conclusao = "";
                if (dataConclusaoServico != null) {
                    conclusao = Helper.dataPadraoBR(dataConclusaoServico);
                }

                String status = "";
                if (statusAgendamento.equals("A")) {
                    status = "Em aberto";
                } else if (statusAgendamento.equals("D")) {
                    status = "Finalizado";
                }

                System.out.println("ID: " + id);
                System.out.println("ID do funcionário: \t" + idFuncionario);
                System.out.println("Código do serviço: \t" + idServico);
                System.out.println("Data de cadastro: \t" + Helper.dataPadraoBR(dataCadastro));
                System.out.println("Previsão de entrega: \t" + Helper.dataPadraoBR(dataPrevisaoEntrega));
                System.out.println("Conclusão do serviço: \t" + conclusao);
                System.out.println("Status do agendamento: \t" + status);
                System.out.println("Observações: \t\t" + observacao);
                System.out.println("---------------------------");
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

    public boolean apagarAgendamento(int idAgendamento) {
        boolean resposta = false;

        // desativo a verificação de chave estrangeira
        String sqlDisableFKCheck = "SET FOREIGN_KEY_CHECKS = 0";
        boolean aux = au.apagarVinculo(idAgendamento);
        if(aux == false){
            System.out.println("Nâo é possível apagar, dados em uso");
        }
               
        boolean desativarFK = conn.executar(sqlDisableFKCheck);
        if (!desativarFK) {
            System.out.println("Permissão negada para desativar verificação de chave estrangeira.");
            conn.desconectar();
            return false;
        }

        String sqlDel = "DELETE FROM agendamento WHERE id_agendamento = " + idAgendamento;
        resposta = conn.executar(sqlDel);

        // reativo verificação de chave estrangeira
        String sqlEnableFKCheck = "SET FOREIGN_KEY_CHECKS = 1";
        boolean reativarFK = conn.executar(sqlEnableFKCheck);

        if (!reativarFK) {
            System.out.println("Aviso: Não foi possível reativar a verificação de chave estrangeira. Verifique o estado do banco de dados.");
        }
        conn.desconectar();
        return resposta;
    }

    public boolean finalizarAgendamento(int idAgendamento, String conclusao) {
        boolean resposta = false;

        String sql = "UPDATE agendamento set status_agendamento = 'D', data_conclusao = '" + conclusao + "' where id_agendamento = " + idAgendamento;
        resposta = conn.executar(sql);

        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }*/
    
    public int getIdAgendamento() { return idAgendamento; }
    public void setIdAgendamento(int idAgendamento) { this.idAgendamento = idAgendamento; }
    
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setServico(Servico servico) { this.servico = servico; }
    
    public String getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(String dataCadastro) { this.dataCadastro = dataCadastro; }
    public String getDataPrevisaoEntrega() { return dataPrevisao; }
    public void setDataPrevisaoEntrega(String dataPrevisaoEntrega) { this.dataPrevisao = dataPrevisaoEntrega; }
    public String getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(String dataConclusao) { this.dataConclusao = dataConclusao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
