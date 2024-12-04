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
                + "'" + dataPrevisaoEntrega + "', null, 'A', '" + observacao + "')";
        boolean resposta = conn.executar(sqlInserir);
        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

    public boolean editarAgendamento(int idAgendamento, int cliente, int servico, int funcionario, String dataPrevisaoEntrega, String obs) {
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
                    conclusao = h.dataPadraoBR(dataConclusaoServico);
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
                System.out.println("Data de cadastro: \t" + h.dataPadraoBR(dataCadastro));
                System.out.println("Previsão de entrega: \t" + h.dataPadraoBR(dataPrevisaoEntrega));
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
                + "a.id_agendamento, cl.nome_cliente, s.desc_servico, f.nome_funcionario, a.data_cadastro, a.data_previsao_entrega, a.data_conclusao, "
                + "a.status_agendamento "
                + "FROM agendamento a "
                + "JOIN cliente cl ON cl.id_cliente = a.fk_cliente "
                + "JOIN tipo_servico s ON a.fk_servico = s.id_servico "
                + "JOIN funcionario f ON a.fk_funcionario = f.id_funcionario;";

        System.out.println("\nID | RESPONSAVEL | SERVIÇO | AGENDADO EM | PREVISTO PARA | CONCLUÍDO EM | STATUS");
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        String status = "";
        String concluido = "";
        String dataConclusao = "";
        try {
            while (lista.next()) {
                int id = lista.getInt("id_agendamento");
                String funcionario = lista.getString("f.nome_funcionario");
                String servico = lista.getString("s.desc_servico");
                String dataCadastro = h.dataPadraoBR(lista.getString("a.data_cadastro"));
                String dataPrevisao = h.dataPadraoBR(lista.getString("a.data_previsao_entrega"));
                if(lista.getString("a.data_conclusao") != null){
                    dataConclusao = h.dataPadraoBR(lista.getString("a.data_conclusao"));
                }else{
                    dataConclusao = "Em aberto";
                }
                String statusAg = lista.getString("a.status_agendamento");

                if (statusAg.equals("A")) {
                    status = "Em aberto";
                } else if (statusAg.equals("D")) {
                    status = "Finalizado";
                }
                
                System.out.printf("%s.  %s  | %s | %s | %s | %s\t | %s\n",
                        id, funcionario, servico, dataCadastro, dataPrevisao, dataConclusao, status);
            }

            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

    public int verificaRegistro() {
        String sqlConsulta = "SELECT * from agendamento";
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        int contagem = 0;
        try {
            while (lista.next()) {
                contagem++;
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
        return contagem;
    }
}
