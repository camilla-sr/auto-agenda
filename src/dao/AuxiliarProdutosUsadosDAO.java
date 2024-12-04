package dao;

import include.Conexao;
import include.Helper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuxiliarProdutosUsadosDAO {
    final Conexao conn = new Conexao();
    Helper h = new Helper();
    private int idProdutoUsado;
    private int estoque;
    private int agendamento;
    private int qntdUsada;

    // -------------- M�TODOS PRINCIPAIS ---------------
    public void cadastrarVinculo(int estoque, int agendamento, int qndUsada) {
        String sqlInserir = "INSERT into aux_prod_usados (fk_estoque, fk_agendamento, qntd_usada)"
                + "VALUES ("+ estoque +", "+ agendamento +", "+ qntdUsada +")";

        boolean resposta = conn.executar(sqlInserir);
        if (resposta == true) {
            System.out.println("V�nculo criado");
        } else {
            System.out.println("Algo deu errado");
        }
        conn.desconectar();
    }
    
    public void editarVinculo(int idProdutoUsado, int estoque, int agendamento) {
        int auxiliarValido = validaID(idProdutoUsado);

        if (auxiliarValido == 2) {
            System.out.println("Auxiliar n�o encontrado na base");
        } else {
            String sqlEdit = "UPDATE aux_prod_usados set fk_estoque = " + estoque
                    + ", fk_agendamento = " + agendamento + " where id_prod_usado = " + idProdutoUsado + "";

            boolean resposta = conn.executar(sqlEdit);
            if (resposta == true) {
                System.out.println("V�nculo editado");
            } else {
                System.out.println("Algo deu errado");
            }
        }
        conn.desconectar();
    }
    
    public void listarVinculos() {
        String sqlConsulta = "SELECT * from aux_prod_usados";
        System.out.println("-----------Listando V�nculos------------");
        ResultSet lista = conn.executarConsulta(sqlConsulta);

        try {
            while (lista.next()) {
                int id = lista.getInt("id_peca");
                int fk_estoque = lista.getInt("fk_estoque");
                int fk_agenda = lista.getInt("fk_agendamento");

                System.out.println("ID: " + id);
                System.out.println("Estoque: " + fk_estoque);
                System.out.println("Agendamento: " + fk_agenda);
                System.out.println("---------------------------");
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }
    
    public void apagarVinculo(int idProdutoUsado) {
        int auxiliarValido = validaID(idProdutoUsado);
        
        if (auxiliarValido == 2) {
            System.out.println("V�nculo n�o encontrado na base");
        } else {
            String sqlDel = "DELETE from peca where id_peca = " + idProdutoUsado + "";
            boolean resposta = conn.executar(sqlDel);
            if (resposta == true) {
                System.out.println("Vinculo deletado");
            } else {
                System.out.println("Algo deu errado");
            }
        }
        conn.desconectar();
    }
    
    // -------------- M�TODOS DE APOIO ---------------    
    private int validaID(int id) {
        int resposta = 0;
        try {
            String sql = "SELECT * from aux_prod_usados where id_produto_usado = " + id + "";
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
    
    public boolean atualizarQuantidadeEstoque(int idEstoque, int quantidadeUsada) {
        
        String sqlAtualizar = "UPDATE estoque SET quantidade = quantidade - " + quantidadeUsada 
                + " WHERE id_estoque = " + idEstoque + " AND quantidade >= " + quantidadeUsada;

        boolean resposta = conn.executar(sqlAtualizar);
        if (!resposta) {
            System.out.println("Erro: N�o foi poss�vel atualizar o estoque.\n Verifique se a quantidade � suficiente.\n");
        }
        return resposta;
    }

    public boolean atualizarQuantidadePeca(int idPeca, int quantidadeUsada) {
        String sqlAtualizar = "UPDATE estoque SET quantidade = quantidade - " + quantidadeUsada 
                + " WHERE fk_peca = " + idPeca + " AND quantidade >= " + quantidadeUsada;

        boolean resposta = conn.executar(sqlAtualizar);
        if (!resposta) {
            System.out.println("Erro: N�o foi poss�vel atualizar a quantidade da pe�a.\n Verifique se a quantidade � suficiente.\n");
        }
        return resposta;
    }

    public Integer buscarIdEstoque(String identificador) {
        Integer idEstoque = null;

        // Verificar se o identificador � num�rico (para pe�a) ou n�o (para lote)
        Integer idPeca = h.isNumeric(identificador);  // Utilizando seu m�todo isNumeric

        if (idPeca != null) {
            // Caso seja num�rico, busca pelo ID da pe�a
            String sqlBusca = "SELECT id_estoque FROM estoque WHERE fk_peca = " + idPeca;
            ResultSet rs = conn.executarConsulta(sqlBusca);

            try {
                if (rs != null && rs.next()) {
                    idEstoque = rs.getInt("id_estoque");
                }
                rs.close();
            } catch (SQLException e) {
                System.out.println("Erro ao buscar ID do estoque para pe�a: " + e.getMessage());
            }
        } else {
            // Caso n�o seja num�rico, busca pelo c�digo do lote
            String sqlBusca = "SELECT id_estoque FROM estoque WHERE fk_lote = '" + identificador + "'";
            ResultSet rs = conn.executarConsulta(sqlBusca);

            try {
                if (rs != null && rs.next()) {
                    idEstoque = rs.getInt("id_estoque");
                }
                rs.close();
            } catch (SQLException e) {
                System.out.println("Erro ao buscar ID do estoque para lote: " + e.getMessage());
            }
        }

        return idEstoque;
    }

    
    // -------------- GETTERS E SETTERS --------------
    public int getIdProdutoUsado() {
        return idProdutoUsado;
    }
    public void setIdProdutoUsado(int idProdutoUsado) {
        this.idProdutoUsado = idProdutoUsado;
    }
    public int getEstoque() {
        return estoque;
    }
    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
    public int getAgendamento() {
        return agendamento;
    }
    public void setAgendamento(int agendamento) {
        this.agendamento = agendamento;
    }
    public int getQntdUsada() {
        return qntdUsada;
    }
    public void setQntdUsada(int qntdUsada) {
        this.qntdUsada = qntdUsada;
    }
    // ------------------------------------------------
}
