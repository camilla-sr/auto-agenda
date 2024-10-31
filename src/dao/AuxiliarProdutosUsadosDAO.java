package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuxiliarProdutosUsadosDAO {
    final Conexao conn = new Conexao();
    private int idProdutoUsado;
    private int estoque;
    private int agendamento;
    private int qntdUsada;

    // -------------- MÉTODOS PRINCIPAIS ---------------
    public void cadastrarAuxiliar(int estoque, int agendamento, int qndUsada) {
        String sqlInserir = "INSERT into aux_prod_usados (fk_estoque, fk_agendamento, qntd_usada)"
                + "VALUES ("+ estoque +", "+ agendamento +", "+ qntdUsada +")";

        boolean resposta = conn.executar(sqlInserir);
        if (resposta == true) {
            System.out.println("Vínculo criado");
        } else {
            System.out.println("Algo deu errado");
        }
        conn.desconectar();
    }
    
    public void editarPeca(int idProdutoUsado, int estoque, int agendamento) {
        int auxiliarValido = validaID(idProdutoUsado);

        if (auxiliarValido == 2) {
            System.out.println("Auxiliar não encontrado na base");
        } else {
            String sqlEdit = "UPDATE aux_prod_usados set fk_estoque = " + estoque
                    + ", fk_agendamento = " + agendamento + " where id_prod_usado = " + idProdutoUsado + "";

            boolean resposta = conn.executar(sqlEdit);
            if (resposta == true) {
                System.out.println("Vínculo editado");
            } else {
                System.out.println("Algo deu errado");
            }
        }
        conn.desconectar();
    }
    
    public void listarVinculos() {
        String sqlConsulta = "SELECT * from aux_prod_usados";
        System.out.println("-----------Listando Vínculos------------");
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
            System.out.println("Vínculo não encontrado na base");
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
    
    // -------------- MÉTODOS DE APOIO ---------------    
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
