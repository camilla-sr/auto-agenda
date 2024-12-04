package dao;

import include.Conexao;
import include.Helper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoteDAO {

    Helper h = new Helper();
    final Conexao conn = new Conexao();

    public boolean cadastrarLote(String codLote, String dataCompra, String dataVencimento, int qntdGarrafa, String tipoOleo, String hoje) {
        boolean resposta = false;
        boolean loteInserido = false;
        boolean estoqueInserido = false;

        // Início de uma transação
        try {
            // Insere o lote na tabela lote
            String sqlInserirLote = "INSERT INTO lote (cod_lote, data_compra, data_vencimento, und_lote, tipo_oleo) "
                    + "VALUES ('" + codLote + "', '" + dataCompra + "', '" + dataVencimento + "', " + qntdGarrafa + ", '" + tipoOleo + "')";

            loteInserido = conn.executar(sqlInserirLote);
            if (!loteInserido) {
                System.out.println("Erro ao cadastrar o lote.");
                return false;  // Se o lote não for inserido, retorna false e desconecta
            }

            // Recupera o ID do lote recém-inserido
            String sqlConsultaLote = "SELECT cod_lote FROM lote WHERE cod_lote = '" + codLote + "' ORDER BY cod_lote DESC LIMIT 1";
            ResultSet rs = conn.executarConsulta(sqlConsultaLote);

            String idLote = null;
            if (rs.next()) {
                idLote = rs.getString("cod_lote"); // Obtém o ID do lote
            } else {
                System.out.println("Erro ao recuperar o ID do lote.");
                return false;  // Se o ID não for recuperado, retorna false
            }

            // Insere no estoque
            String sqlInserirEstoque = "INSERT INTO estoque (fk_peca, fk_lote, quantidade, data_ultima_atualizacao) "
                    + "VALUES (null, '" + idLote + "', " + qntdGarrafa + ", '" + hoje + "')";

            estoqueInserido = conn.executar(sqlInserirEstoque);
            if (!estoqueInserido) {
                System.out.println("Erro ao cadastrar no estoque.");
                rollback(codLote);
                return false;
            }
            resposta = true;  // Se ambos os inserts foram bem-sucedidos, retorna true
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar lote: " + e.getMessage());
            return false;
        } finally {
            conn.desconectar();
        }
        return resposta;
    }

    public boolean editarLote(String codLote, String novaDataCompra, String novaDataVencimento, int novaQntdGarrafa, String novoTipoOleo, String hoje) {
        String sqlEdit = "";
        String sqlEditEstoque = "";

        // TIPO ÓLEO
        if (!novoTipoOleo.isEmpty() && novaDataCompra.isEmpty() && novaDataVencimento.isEmpty() && novaQntdGarrafa == 0) {
            sqlEdit = "UPDATE lote SET tipo_oleo = '" + novoTipoOleo + "' WHERE cod_lote = '" + codLote + "'";
        }
        // QUANTIDADE GARRAFAS
        if (novaQntdGarrafa != 0 && novaDataCompra.isEmpty() && novaDataVencimento.isEmpty() && novoTipoOleo.isEmpty()) {
            sqlEdit = "UPDATE lote SET und_lote = " + novaQntdGarrafa + " WHERE cod_lote = '" + codLote + "'";
            sqlEditEstoque = "UPDATE estoque SET quantidade = " + novaQntdGarrafa + ", data_ultima_atualizacao = '" + hoje + "' WHERE fk_lote = '" + codLote + "'";
        }
        // DATA DA COMPRA
        if (!novaDataCompra.isEmpty() && novaDataVencimento.isEmpty() && novaQntdGarrafa == 0 && novoTipoOleo.isEmpty()) {
            sqlEdit = "UPDATE lote SET data_compra = '" + novaDataCompra + "' WHERE cod_lote = '" + codLote + "'";
        }
        // DATA DO VENCIMENTO
        if (!novaDataVencimento.isEmpty() && novaDataCompra.isEmpty() && novaQntdGarrafa == 0 && novoTipoOleo.isEmpty()) {
            sqlEdit = "UPDATE lote SET data_vencimento = '" + novaDataVencimento + "' WHERE cod_lote = '" + codLote + "'";
        }
        // TIPO ÓLEO E QUANTIDADE
        if (!novoTipoOleo.isEmpty() && novaQntdGarrafa != 0 && novaDataCompra.isEmpty() && novaDataVencimento.isEmpty()) {
            sqlEdit = "UPDATE lote SET tipo_oleo = '" + novoTipoOleo + "', und_lote = " + novaQntdGarrafa;
            sqlEditEstoque = "UPDATE estoque SET quantidade = " + novaQntdGarrafa + ", data_ultima_atualizacao = '" + hoje + "' WHERE fk_lote = '" + codLote + "'";
        }
        // DATAS DE COMPRA E VENCIMENTO
        if (!novaDataVencimento.isEmpty() && !novaDataCompra.isEmpty() && novaQntdGarrafa == 0 && novoTipoOleo.isEmpty()) {
            sqlEdit = "UPDATE lote SET data_compra = '" + novaDataCompra + "', data_vencimento = '" + novaDataVencimento + "' WHERE cod_lote = '" + codLote + "'";
        }
        // TODOS OS CAMPOS
        if (!novaDataCompra.isEmpty() && !novaDataVencimento.isEmpty() && novaQntdGarrafa != 0 && !novoTipoOleo.isEmpty()) {
            sqlEdit = "UPDATE lote SET data_compra = '" + novaDataCompra + "', data_vencimento = '" + novaDataVencimento
                    + "', und_lote = " + novaQntdGarrafa + ", tipo_oleo = '" + novoTipoOleo + "' WHERE cod_lote = '" + codLote + "'";
            sqlEditEstoque = "UPDATE estoque SET quantidade = " + novaQntdGarrafa + ", data_ultima_atualizacao = '" + hoje + "' WHERE fk_lote = '" + codLote + "'";
        }
        boolean resposta = false;

        resposta = conn.executar(sqlEdit);
        
        if (!resposta) {
            System.out.println("Erro ao atualizar o lote.");
            conn.desconectar();
            return false;
        }

        if (!sqlEditEstoque.isEmpty()) {
            resposta = conn.executar(sqlEditEstoque);
            if (!resposta) {
                System.out.println("Erro ao atualizar a quantidade no estoque.");
                conn.desconectar();
                return false;
            }
        }
        return true;  // Se ambos os updates forem bem-sucedidos
    }

    public void listarLote() {
        String sqlConsulta = "SELECT * from lote";
        System.out.println("---------------------------");
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        try {
            if (lista != null) {
                while (lista.next()) {
                    String dataCompra = h.dataPadraoBR(lista.getString("data_compra"));
                    String dataVenci = h.dataPadraoBR(lista.getString("data_vencimento"));

                    System.out.println("Código do lote: \t\t" + lista.getString("cod_lote"));
                    System.out.println("Data da Compra: \t\t" + dataCompra);
                    System.out.println("Data de Vencimento: \t\t" + dataVenci);
                    System.out.println("Quantidade de garrafas: \t" + lista.getInt("und_lote"));
                    System.out.println("Tipo de Óleo: \t\t\t" + lista.getString("tipo_oleo"));
                    System.out.println("---------------------------");
                }
            } else {
                System.out.println("\tNenhum lote cadastrado");
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

    public boolean apagarLote(String codLote) {
        boolean resposta = false;

        String sqlDelEstoque = "DELETE from estoque WHERE fk_lote = '" + codLote + "'";
        resposta = conn.executar(sqlDelEstoque);

        if (resposta) {
            String sqlDelLote = "DELETE from lote WHERE cod_lote = '" + codLote + "'";
            resposta = conn.executar(sqlDelLote);
            if (resposta) {
                conn.desconectar();
                return true;
            } else {
                System.out.println("Erro ao apagar o lote da tabela 'lote'.");
                conn.desconectar();
                return false;
            }
        } else {
            System.out.println("Erro ao apagar o lote da tabela 'estoque'.");
            conn.desconectar();
            return false;
        }
    }

    // -------------- MÉTODOS DE APOIO --------------
    public int validaCOD(String codLote) {
        int resposta = 0;
        try {
            String sql = "SELECT * from lote where cod_lote = '" + codLote + "'";
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

    public void listaEdicao() {
        String sqlConsulta = "SELECT * from lote";
        System.out.println("\nCÓDIGO | TIPO DE ÓLEO | DATA DA COMPRA");
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        try {
            while (lista.next()) {
                String cod = lista.getString("cod_lote");
                String dataCompra = h.dataPadraoBR(lista.getString("data_compra"));
                String tipoOleo = lista.getString("tipo_oleo");

                System.out.printf("%s |  %s  | %s\n", cod, tipoOleo, dataCompra);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

    public int verificaRegistro() {
        String sqlConsulta = "SELECT * from lote";
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
    
    public boolean rollback(String codLote) {
        boolean resposta = false;

        String sqlDelEstoque = "DELETE from lote WHERE cod_lote = '" + codLote + "'";
        resposta = conn.executar(sqlDelEstoque);

        if (resposta) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }
}
