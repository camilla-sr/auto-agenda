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

        // TIPO ÓLEO
        if (!novoTipoOleo.isEmpty() && novaDataCompra.isEmpty() && novaDataVencimento.isEmpty() && novaQntdGarrafa == 0) {
            sqlEdit = "UPDATE lote SET tipo_oleo = '" + novoTipoOleo + "'";
        }
        // QUANTIDADE GARRAFAS
        if (novaQntdGarrafa != 0 && novaDataCompra.isEmpty() && novaDataVencimento.isEmpty() && novoTipoOleo.isEmpty()) {
            sqlEdit = "UPDATE lote SET und_lote = " + novaQntdGarrafa;
        }
        // DATA DA COMPRA
        if (!novaDataCompra.isEmpty() && novaDataVencimento.isEmpty() && novaQntdGarrafa == 0 && novoTipoOleo.isEmpty()) {
            sqlEdit = "UPDATE lote SET data_compra = '" + novaDataCompra + "'";
        }
        // DATA DO VENCIMENTO
        if (!novaDataVencimento.isEmpty() && novaDataCompra.isEmpty() && novaQntdGarrafa == 0 && novoTipoOleo.isEmpty()) {
            sqlEdit = "UPDATE lote SET data_vencimento = '" + novaDataVencimento + "'";
        }
        // TIPO ÓLEO E QUANTIDADE
        if (!novoTipoOleo.isEmpty() && novaQntdGarrafa != 0 && novaDataCompra.isEmpty() && novaDataVencimento.isEmpty()) {
            sqlEdit = "UPDATE lote SET tipo_oleo = '" + novoTipoOleo + "', und_lote = " + novaQntdGarrafa;
        }      
        // DATAS DE COMPRA E VENCIMENTO
        if (!novaDataVencimento.isEmpty() && !novaDataCompra.isEmpty() && novaQntdGarrafa == 0 && novoTipoOleo.isEmpty()) {
            sqlEdit = "UPDATE lote SET data_compra = '" + novaDataCompra + "', data_vencimento = '" + novaDataVencimento + "'";
        }
        // TODOS OS CAMPOS
        if (!novaDataCompra.isEmpty() && !novaDataVencimento.isEmpty() && novaQntdGarrafa != 0 && !novoTipoOleo.isEmpty()) {
            sqlEdit = "UPDATE lote SET data_compra = '" + novaDataCompra + "', data_vencimento = '" + novaDataVencimento
                    + "', und_lote = " + novaQntdGarrafa + ", tipo_oleo = '" + novoTipoOleo + "'";
        }

        sqlEdit = sqlEdit + " WHERE cod_lote = '" + codLote + "'";  // finalizo a variável com a query completa

        boolean resposta = conn.executar(sqlEdit);
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
        System.out.println("---------------------------");
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        try {
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
    
    public int verificaRegistro(){
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
}
