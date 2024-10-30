package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgendamentoDAO {

    final Conexao conn = new Conexao();
    private int idAgendamento;
    private int servico;
    private int funcionario;
    private String dataCadastro;
    private String dataPrevisaoEntrega;
    private String dataConclusao;

    // Métodos Principais
    public void cadastrarAgendamento(int servico, int funcionario, String dataCadastro, String dataPrevisaoEntrega, String dataConclusao) {
        String sqlInserir = "INSERT into agendamento (id_servico, id_funcionario, String data_cadastro, data_previsao_entrega, data_conclusao_servico)"
                + "VALUES (" + servico + ", " + funcionario + ", '" + dataCadastro + "', '" + dataPrevisaoEntrega + "', '" + dataConclusao + "')";

        boolean resposta = conn.executar(sqlInserir);
        if (resposta == true) {
            System.out.println("Agendamento inserido");
        } else {
            System.out.println("Algo deu errado");
        }
        conn.desconectar();
    }

    public void editarAgendamento(int idAgendamento, int servico, int funcionario, String dataCadastro, String dataPrevisaoEntrega, String dataConclusao) {
        int agendamentoValida = validaID(idAgendamento);

        if (agendamentoValida == 2) {
            System.out.println("Agendamento não encontrado");
        } else {
            String sqlEdit = "UPDATE agendamento set id_servico = " + servico + ", id_funcionario = " + funcionario + ", data_cadastro = "
                    + dataCadastro + ", data_previsao_entrega = " + dataPrevisaoEntrega + ", data_conclusao_servico = " + dataConclusao
                    + " WHERE id_agendamento = " + idAgendamento + "";

            boolean resposta = conn.executar(sqlEdit);
            if (resposta == true) {
                System.out.println("Agendamento editado");
            } else {
                System.out.println("Algo deu errado");
            }
        }
        conn.desconectar();
    }

    public void listarAgendamento() {
        String sqlConsulta = "SELECT * from agendamento";
        System.out.println("---------------------------");
        ResultSet lista = conn.executarConsulta(sqlConsulta);

        try {
            while (lista.next()) {
                int id = lista.getInt("id_agendamento");
                int idFuncionario = lista.getInt("id_funcionario");
                int idServico = lista.getInt("id_servico");
                String dataCadastro = lista.getString("data_cadastro");
                String dataPrevisaoEntrega = lista.getString("data_previsao_entrega");
                String dataConclusaoServico = lista.getString("data_conclusao");
                String statusAgendamento = lista.getString("status_agendamento");

                System.out.println("ID: " + id);
                System.out.println("ID do funcionário: " + idFuncionario);
                System.out.println("Código do serviço: " + idServico);
                System.out.println("Data de cadastro: " + dataCadastro);
                System.out.println("Previsão de entrega: " + dataPrevisaoEntrega);
                System.out.println("Conclusão do serviço: " + dataConclusaoServico);
                System.out.println("Status do agendamento: " + statusAgendamento);
                System.out.println("---------------------------");
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

    public void apagarAgendamento(int idAgendamento) {
        int agendamentoValida = validaID(idAgendamento);

        if (agendamentoValida == 2) {
            System.out.println("Agendamento não encontrado");
        } else {
            String sqlDel = "DELETE from agendamento WHERE id_agendamento = " + idAgendamento + "";
            boolean resposta = conn.executar(sqlDel);
            if (resposta == true) {
                System.out.println("Agendamento deletado");
            } else {
                System.out.println("Algo deu errado");
            }
        }
        conn.desconectar();
    }

    // -------------- MÉTODOS DE APOIO ---------------
    private int validaID(int id) {
        int resposta = 0;
        try {
            String sql = "SELECT * from agendamento WHERE id_agendamento = " + id + "";
            ResultSet retorno = conn.executarConsulta(sql);
            if (retorno != null && retorno.next()) {
                resposta = 1;
            } else {
                resposta = 2;
            }
        } catch (SQLException e) {
            System.out.println("Algo deu errado" + e.getMessage());
        } finally {
            conn.desconectar();
        }
        return resposta;
    }

    // -------------- GETTERS E SETTERS --------------
    public int getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(int idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public int getServico() {
        return servico;
    }

    public void setServico(int servico) {
        this.servico = servico;
    }

    public int getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(int funcionario) {
        this.funcionario = funcionario;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getDataPrevisaoEntrega() {
        return dataPrevisaoEntrega;
    }

    public void setDataPrevisaoEntrega(String dataPrevisaoEntrega) {
        this.dataPrevisaoEntrega = dataPrevisaoEntrega;
    }

    public String getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(String dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
// ------------------------------------------------    
}
