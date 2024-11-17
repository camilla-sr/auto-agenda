package dao;

import include.Conexao;
import include.Helper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoteDAO {
    Helper h = new Helper();
    final Conexao conn = new Conexao();

    public boolean cadastrarLote(String codLote, String dataCompra, String dataVencimento, int qntdGarrafa, String tipoOleo) {
        boolean resposta = false;
        String sqlInserir = "INSERT into lote(cod_lote, data_compra, data_vencimento, und_lote, tipo_oleo) VALUES ('"
                + codLote + "', '" + dataCompra + "', '" + dataVencimento + "', " + qntdGarrafa + ", '" + tipoOleo + "')";

        resposta = conn.executar(sqlInserir);
        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

    public boolean editarLote(String codLote, String novaDataCompra, String novaDataVencimento, int novaQntdGarrafa, String novoTipoOleo) {
        String sqlEdit = "";  // Inicializo a variável da query

        // DATA DA COMPRA
        if ((novaDataCompra != "" || novaDataCompra != null) && (novaDataVencimento == "" || novaDataVencimento == null)
                && novaQntdGarrafa == 0 && (novoTipoOleo == "" || novoTipoOleo == null)) {
            sqlEdit = "UPDATE lote SET data_compra = '" + novaDataCompra + "'";
        }

        // DATA DO VENCIMENTO
        if ((novaDataVencimento != "" || novaDataVencimento != null) && (novaDataCompra == "" || novaDataCompra == null)
                && novaQntdGarrafa == 0 && (novoTipoOleo == "" || novoTipoOleo == null)) {
            sqlEdit = "UPDATE lote SET data_vencimento = '" + novaDataVencimento + "'";
        }

        // QUANTIDADE GARRAFAS
        if (novaQntdGarrafa != 0 && (novaDataCompra == "" || novaDataCompra == null)
                && (novaDataVencimento == "" || novaDataVencimento == null) && (novoTipoOleo == "" || novoTipoOleo == null)) {
            sqlEdit = "UPDATE lote SET und_lote = " + novaQntdGarrafa;
        }

        // TIPO ÓLEO
        if ((novoTipoOleo != "" || novoTipoOleo != null) && (novaDataCompra == "" || novaDataCompra == null)
                && (novaDataVencimento == "" || novaDataVencimento == null) && novaQntdGarrafa == 0) {
            sqlEdit = "UPDATE lote SET tipo_oleo = '" + novoTipoOleo + "'";
        }

        // Se o usuário modificou mais de um campo
        if ((novaDataCompra != "" || novaDataCompra != null) && (novaDataVencimento != "" || novaDataVencimento != null)
                && novaQntdGarrafa != 0 && (novoTipoOleo != "" || novoTipoOleo != null)) {
            sqlEdit = "UPDATE lote SET data_compra = '" + novaDataCompra + "', data_vencimento = '" + novaDataVencimento
                    + "', und_lote = " + novaQntdGarrafa + ", tipo_oleo = '" + novoTipoOleo + "'";
        }

        sqlEdit = sqlEdit + " WHERE cod_lote = '" + codLote + "'";

        // Executo a query de atualização no banco
        boolean resposta = conn.executar(sqlEdit);

        // Retorno o resultado da operação
        if (resposta) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

    public void listarLote() {
        String sqlConsulta = "SELECT * from lote";
        System.out.println("Listando Lotes");
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        try {
            while (lista.next()) {
                String dataCompra = lista.getString("data_compra");
                String dataVenci = lista.getString("data_vencimento");

                System.out.println("\nCódigo do lote: \t\t" + lista.getString("cod_lote"));
                System.out.println("Data da Compra: \t\t" + h.dataPadraoBR(dataCompra));
                System.out.println("Data de Vencimento: \t\t" + h.dataPadraoBR(dataVenci));
                System.out.println("Quantidade de garrafas: \t" + lista.getInt("und_lote"));
                System.out.println("Tipo de Óleo: \t\t\t" + lista.getString("tipo_oleo"));
                System.out.println("---------------------------");
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

        String sqlDel = "DELETE from lote where cod_lote = '" + codLote + "'";
        resposta = conn.executar(sqlDel);
        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
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
                String dataCompra = lista.getString("data_compra");
                String tipoOleo = lista.getString("tipo_oleo");

                System.out.printf("%s.  %s  | %s\n", cod, tipoOleo, dataCompra);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }
}
