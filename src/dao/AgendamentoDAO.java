package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import include.Helper;

public class AgendamentoDAO {

    final Conexao conn = new Conexao();
    Helper h = new Helper();

    // M�todos Principais
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
        String sqlEdit = ""; // Inicializo a vari�vel da query

        // Atualizar apenas o Cliente
        if (cliente != 0 && servico == 0 && funcionario == 0 && dataPrevisaoEntrega == null && obs == null) {
            sqlEdit = "UPDATE agendamento SET fk_cliente = " + cliente;
        }
        // Atualizar apenas o Servi�o
        if (servico != 0 && cliente == 0 && funcionario == 0 && dataPrevisaoEntrega == null && obs == null) {
            sqlEdit = "UPDATE agendamento SET fk_servico = " + servico;
        }
        // Atualizar apenas o Funcion�rio
        if (funcionario != 0 && cliente == 0 && servico == 0 && dataPrevisaoEntrega == null && obs == null) {
            sqlEdit = "UPDATE agendamento SET fk_funcionario = " + funcionario;
        }
        // Atualizar apenas a Data de Previs�o de Entrega
        if (dataPrevisaoEntrega != null && cliente == 0 && servico == 0 && funcionario == 0 && obs == null) {
            sqlEdit = "UPDATE agendamento SET data_previsao_entrega = '" + dataPrevisaoEntrega + "'";
        }
        // Atualizar apenas a Observa��o
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
                System.out.println("ID do funcion�rio: \t" + idFuncionario);
                System.out.println("C�digo do servi�o: \t" + idServico);
                System.out.println("Data de cadastro: \t" + h.dataPadraoBR(dataCadastro));
                System.out.println("Previs�o de entrega: \t" + h.dataPadraoBR(dataPrevisaoEntrega));
                System.out.println("Conclus�o do servi�o: \t" + conclusao);
                System.out.println("Status do agendamento: \t" + status);
                System.out.println("Observa��es: \t\t" + observacao);
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

        // desativo a verifica��o de chave estrangeira
        String sqlDisableFKCheck = "SET FOREIGN_KEY_CHECKS = 0";
        boolean desativarFK = conn.executar(sqlDisableFKCheck);

        if (!desativarFK) {
            System.out.println("Permiss�o negada para desativar verifica��o de chave estrangeira.");
            conn.desconectar();
            return false;
        }

        String sqlDel = "DELETE FROM agendamento WHERE id_agendamento = " + idAgendamento;
        resposta = conn.executar(sqlDel);

        // reativo verifica��o de chave estrangeira
        String sqlEnableFKCheck = "SET FOREIGN_KEY_CHECKS = 1";
        boolean reativarFK = conn.executar(sqlEnableFKCheck);

        if (!reativarFK) {
            System.out.println("Aviso: N�o foi poss�vel reativar a verifica��o de chave estrangeira. Verifique o estado do banco de dados.");
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

    // -------------- M�TODOS DE APOIO ---------------
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
            System.out.println("Agendamento n�o encontrado");
        } else {
            // aqui ser� inclu�do na l�gica as listas de produtos para que o usu�rio as insira aqui

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

        System.out.println("\nID | RESPONSAVEL | SERVI�O | AGENDADO EM | PREVISTO PARA | CONCLU�DO EM | STATUS");
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
                if (lista.getString("a.data_conclusao") != null) {
                    dataConclusao = h.dataPadraoBR(lista.getString("a.data_conclusao"));
                } else {
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

    public void relatorio() {
        // SQL para obter todos os agendamentos, status, pe�as e lotes
        String sqlConsulta = "SELECT f.nome_funcionario, \n" +
                        "       a.id_agendamento, \n" +
                        "       a.status_agendamento,\n" +
                        "       COUNT(DISTINCT apu.fk_estoque) AS total_produtos_usados,\n" +
                        "       SUM(apu.qntd_usada) AS total_qntd_usada\n" +
                        "FROM agendamento a\n" +
                        "JOIN funcionario f ON a.fk_funcionario = f.id_funcionario\n" +
                        "LEFT JOIN aux_prod_usados apu ON a.id_agendamento = apu.fk_agendamento\n" +
                        "LEFT JOIN estoque e ON apu.fk_estoque = e.id_estoque\n" +
                        "GROUP BY f.nome_funcionario, a.id_agendamento, a.status_agendamento\n" +
                        "ORDER BY f.nome_funcionario, a.id_agendamento";

        ResultSet lista = conn.executarConsulta(sqlConsulta);
        System.out.println("---------------------------");

        try {
            if (lista != null) {
                // Vari�veis de contagem
                int agendamentosAbertos = 0;
                int agendamentosFinalizados = 0;
                int totalPecasUsadas = 0;
                int totalLotesUsados = 0;

                String nomeFuncionarioAtual = "";

                while (lista.next()) {
                    String nomeFuncionario = lista.getString("nome_funcionario");
                    int idAgendamento = lista.getInt("id_agendamento");
                    String statusAgendamento = lista.getString("status_agendamento");
                    Integer fkPeca = lista.getInt("fk_peca");
                    Integer fkLote = lista.getInt("fk_lote");
                    int quantidade = lista.getInt("quantidade");

                    // Verificar se o funcion�rio mudou (iniciar novo funcion�rio)
                    if (!nomeFuncionario.equals(nomeFuncionarioAtual)) {
                        // Exibir os resultados do funcion�rio anterior, se houver
                        if (!nomeFuncionarioAtual.isEmpty()) {
                            System.out.println("Funcion�rio: " + nomeFuncionarioAtual);
                            System.out.println("Total de agendamentos: " + verificaRegistro());
                            System.out.println("Agendamentos em aberto: " + agendamentosAbertos);
                            System.out.println("Agendamentos finalizados: " + agendamentosFinalizados);
                            System.out.println("Total de pe�as usadas: " + totalPecasUsadas);
                            System.out.println("Total de lotes usados: " + totalLotesUsados);
                            System.out.println("---------------------------");
                        }
                        // Resetar as vari�veis para o novo funcion�rio
                        nomeFuncionarioAtual = nomeFuncionario;
                        agendamentosAbertos = 0;
                        agendamentosFinalizados = 0;
                        totalPecasUsadas = 0;
                        totalLotesUsados = 0;
                    }

                    // Contar agendamentos em aberto ou finalizados
                    if ("A".equals(statusAgendamento)) {
                        agendamentosAbertos++;
                    } else if ("F".equals(statusAgendamento)) {
                        agendamentosFinalizados++;
                    }

                    // Verificar se a pe�a foi usada
                    if (fkPeca != null) {
                        totalPecasUsadas += quantidade;
                    }

                    // Verificar se o lote foi usado
                    if (fkLote != null) {
                        totalLotesUsados += quantidade;
                    }
                }

                // Exibir o relat�rio final para o �ltimo funcion�rio
                if (!nomeFuncionarioAtual.isEmpty()) {
                    System.out.println("Funcion�rio: " + nomeFuncionarioAtual);
                    System.out.println("Total de agendamentos: " + verificaRegistro());
                    System.out.println("Agendamentos em aberto: " + agendamentosAbertos);
                    System.out.println("Agendamentos finalizados: " + agendamentosFinalizados);
                    System.out.println("Total de pe�as usadas: " + totalPecasUsadas);
                    System.out.println("Total de lotes usados: " + totalLotesUsados);
                    System.out.println("---------------------------");
                }
            } else {
                System.out.println("\tNenhum agendamento encontrado.");
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }
}
