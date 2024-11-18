package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import include.Helper;

public class AgendamentoDAO {
    final Conexao conn = new Conexao();
    Helper h = new Helper();

    // Métodos Principais
    public boolean cadastrarAgendamento(int cliente, int servico, int funcionario, String dataCadastro, String dataPrevisaoEntrega, String observacao) {
        String sqlInserir = "INSERT into agendamento (fk_cliente, fk_servico, fk_funcionario, data_cadastro, data_previsao_entrega, data_conclusao, status_agendamento, observacao)"
                + "VALUES (" + cliente + "," + servico + ", " + funcionario + ", '" + dataCadastro + "', "
                + "'" + dataPrevisaoEntrega + "', '', 'A', '" + observacao + "')";
        boolean resposta = conn.executar(sqlInserir);
        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

    public void editarAgendamento(int idAgendamento, int servico, int funcionario, String dataCadastro, String dataPrevisaoEntrega, String dataConclusao) {
        boolean agendamentoValida = validaID(idAgendamento);

        if (agendamentoValida) {
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

    public boolean apagarAgendamento(int idAgendamento) {
        boolean resposta = false;
        boolean agendamentoValida = validaID(idAgendamento);

        if (!agendamentoValida ) {
            System.out.println("Agendamento não encontrado");
        } else {
            String sqlDel = "DELETE from agendamento WHERE id_agendamento = " + idAgendamento + "";
            resposta = conn.executar(sqlDel);
        }
        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

    public boolean atualizarStatus(int idAgendamento, String novoEstado) {
        boolean resposta = false;
        boolean agendamentoValida = validaID(idAgendamento);

        if (!agendamentoValida) {
            System.out.println("Agendamento não encontrado");
        } else {
            String sql = "UPDATE agendamento set status_agendamento = " + novoEstado + "";
            resposta = conn.executar(sql);
        }
        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

    // -------------- MÉTODOS DE APOIO ---------------
    public boolean validaID(int id) {
        boolean resposta = false;
        try {
            String sql = "SELECT * from agendamento WHERE id_agendamento = " + id + "";
            ResultSet retorno = conn.executarConsulta(sql);
            if (retorno != null && retorno.next()) {
                resposta = true;
            } else {
                resposta = false;
            }
        } catch (SQLException e) {
            System.out.println("Algo deu errado" + e.getMessage());
        } finally {
            conn.desconectar();
        }
        return resposta;
    }

    private boolean cadastrarProdutoUsado(int idAgendamento, int idEstoque, int qntdUsada) {
        boolean insertSecundario = false;
        boolean agendamentoValida = validaID(idAgendamento);

        if (agendamentoValida) {
            System.out.println("Agendamento não encontrado");
        } else {
            // aqui será incluído na lógica as listas de produtos para que o usuário as insira aqui

            String sql = "INSERT into aux_prod_usados(fk_estoque, fk_agendamento, qntd_usada)"
                    + "values (" + idAgendamento + ", " + idEstoque + ", " + qntdUsada + ")";
            boolean resposta = conn.executar(sql);
            if (resposta == true) {
                insertSecundario = true;
            } else {
                insertSecundario = false;
            }
        }
        return insertSecundario;
    }

    public void listaEdicao() {
        String sqlConsulta = "SELECT "
            + "a.id_agendamento, cl.nome_cliente, s.desc_servico, f.nome_funcionario, a.data_cadastro, a.status_agendamento\n"
            + "from agendamento a join cliente cl on cl.id_cliente = a.fk_cliente\n"
            + "join tipo_servico s on a.fk_servico = s.id_servico\n"
            + "join funcionario f on a.fk_funcionario = f.id_funcionario;";

        System.out.println("\nID | FINALIZADO POR | SERVIÇO | AGENDADO EM | STATUS");
        ResultSet lista = conn.executarConsulta(sqlConsulta);

        try {
            while (lista.next()) {
                String id = lista.getString("id_agendamento");
                String funcionario = lista.getString("f.nome_funcionario");
                String servico = lista.getString("s.desc_servico");
                String dataCadastro = lista.getString("a.data_cadastro");
                String statusAgendamento = lista.getString("a.status_agendamento");

                System.out.printf("%d. %s  \t-%s \t%s \t%s\n", id, funcionario, servico, dataCadastro, statusAgendamento);
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }
}
