package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PecaDAO {
    final Conexao conn = new Conexao();
    private int idPeca;
    private String descricaoPeca;
    private int qntdPeca;

    // Métodos Principais
    public void cadastrarPeca(String descricaoPeca, int qntdPeca) {
        String sqlInserir = "INSERT into peca (desc_peca, qntd_peca)"
                + "VALUES ('" + descricaoPeca + "', " + qntdPeca + ")";

        boolean resposta = conn.executar(sqlInserir);
        if (resposta == true) {
            System.out.println("Peça inserida");
        } else {
            System.out.println("Algo deu errado");
        }
        conn.desconectar();
    }

    public void editarPeca(int idPeca, String novaDescricao, int novaQntd) {
        int pecaValida = validaID(idPeca);

        if (pecaValida == 2) {
            System.out.println("Peça não encontrada na base");
        } else {
            String sqlEdit = "UPDATE peca set desc_peca = '" + novaDescricao
                    + "', qntd_peca = " + novaQntd + " where id_peca = " + idPeca + "";

            boolean resposta = conn.executar(sqlEdit);
            if (resposta == true) {
                System.out.println("Peça Editada");
            } else {
                System.out.println("Algo deu errado");
            }
        }
        conn.desconectar();
    }

    public void listarPecas() {
        String sqlConsulta = "SELECT * from peca";
        System.out.println("---------------------------");
        ResultSet lista = conn.executarConsulta(sqlConsulta);

        try {
            while (lista.next()) {
                int id = lista.getInt("id_peca");
                String descricaoPeca = lista.getString("desc_peca");
                int quantidade = lista.getInt("qntd_peca");

                System.out.println("ID: " + id);
                System.out.println("Peça: " + descricaoPeca);
                System.out.println("Quantidade: " + quantidade);
                System.out.println("---------------------------");
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }
    
    public void apagarPeca(int idPeca) {
        int pecaValida = validaID(idPeca);
        if (pecaValida == 2) {
            System.out.println("Peça não encontrada na base");
        } else {
            String sqlDel = "DELETE from peca where id_peca = " + idPeca;
            boolean resposta = conn.executar(sqlDel);
            if (resposta == true) {
                System.out.println("Peça deletada");
            } else {
                System.out.println("Algo deu errado");
            }
        }
        conn.desconectar();
    }

// -------------- MÉTODOS DE APOIO- --------------    
    private int validaID(int id) {
        int resposta = 0;
        try {
            String sql = "SELECT * from peca where id_peca = " + id;
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
    public int getIdPeca() {
        return idPeca;
    }
    public void setIdPeca(int idPeca) {
        this.idPeca = idPeca;
    }
    public String getDescricaoPeca() {
        return descricaoPeca;
    }
    public void setDescricaoPeca(String descricaoPeca) {
        this.descricaoPeca = descricaoPeca;
    }
    public int getQntdPeca() {
        return qntdPeca;
    }
    public void setQntdPeca(int qntdPeca) {
        this.qntdPeca = qntdPeca;
    }
    // -----------------------------------------------
}
