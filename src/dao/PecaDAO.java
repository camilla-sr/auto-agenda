package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PecaDAO {

    final Conexao conn = new Conexao();

    // M�todos Principais
    public boolean cadastrarPeca(String descricaoPeca, int qntdPeca, String dataHoje) {
        boolean resposta = false;
        try {
            // primeiro fa�o a inser��o da descri��o na tabela pe�a
            String sqlInserirPeca = "INSERT INTO peca (desc_peca) VALUES ('" + descricaoPeca + "')";
            boolean pecaInserida = conn.executar(sqlInserirPeca);

            if (!pecaInserida) {
                System.out.println("Erro ao cadastrar");
                return false;
            }

            // recupero o ID da pe�a que acabei de inserir
            String sqlConsultaPeca = "SELECT id_peca FROM peca WHERE desc_peca = '" + descricaoPeca + "' ORDER BY id_peca DESC LIMIT 1";
            ResultSet rs = conn.executarConsulta(sqlConsultaPeca);

            int idPeca = -1; // Inicializa o ID da pe�a com valor inv�lido
            if (rs.next()) {
                idPeca = rs.getInt("id_peca");  //Se a inser��o for feita, pego o ID
            } else {
                System.out.println("Erro ao recuperar o ID da pe�a.");
                return false;
            }

            // dados prontos, preparo a inser��o na tabela do estoque
            String sqlInserirEstoque = "INSERT INTO estoque (fk_peca, fk_lote, quantidade, data_ultima_atualizacao)"
                    + " VALUES (" + idPeca + ", " + null + ", " + qntdPeca + ", '" + dataHoje + "')";
            boolean estoqueInserido = conn.executar(sqlInserirEstoque);

            if (!estoqueInserido) {
                System.out.println("Erro ao cadastrar pe�a no estoque");
                apagarPeca(idPeca);
                return false;
            }
            resposta = true;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar pe�a: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
        return resposta;
    }

    public boolean editarPeca(int idPeca, String novaDescricao, int novaQntd, String dataHoje) {
        String sqlEdit = ""; // inicializo a vari�vel da query
        String sqlEdit2 = ""; // inicializo a vari�vel da query

        // testo o que ser� editado e mudo a descri��o da pe�a
        if (novaDescricao != null && !novaDescricao.isEmpty() && novaQntd == 0) {
            sqlEdit = "UPDATE peca SET desc_peca = '" + novaDescricao + "' where id_peca = " + idPeca;
        }
        // Muda apenas a quantidade
        if (novaQntd != 0 && (novaDescricao == null || novaDescricao.isEmpty())) {
            sqlEdit = "UPDATE estoque SET quantidade = " + novaQntd + ", data_ultima_atualizacao = '" + dataHoje + "' where fk_peca = " + idPeca + "";
        }
        // Muda ambos
        if (novaDescricao != null && !novaDescricao.isEmpty() && novaQntd != 0) {
            sqlEdit = "UPDATE peca SET desc_peca = '" + novaDescricao + "' where id_peca = " + idPeca + "";
            sqlEdit2 = "UPDATE estoque SET quantidade = " + novaQntd + ", data_ultima_atualizacao = '" + dataHoje + "' where fk_peca = " + idPeca + "";
        }

        boolean resposta = false;
        System.out.println(sqlEdit);
        if (!sqlEdit.isEmpty()) {
            resposta = conn.executar(sqlEdit);
            if (!resposta) {
                System.out.println("Erro ao atualizar a descri��o da pe�a.");
                conn.desconectar();
                return false; // Retorna false caso falhe
            }
        }
        System.out.println(sqlEdit);
        System.out.println(sqlEdit2);
        // Executa a atualiza��o da quantidade, se necess�rio
        if (!sqlEdit2.isEmpty()) {
            resposta = conn.executar(sqlEdit2);
            if (!resposta) {
                System.out.println("Erro ao atualizar a quantidade no estoque.");
                conn.desconectar();
                return false; // Retorna false caso falhe
            }
        }
        return true; // Se ambos os updates foram bem-sucedidos
    }

    public void listarPecas() {
        String sqlConsulta = "SELECT p.id_peca, p.desc_peca, e.quantidade from peca p join estoque e on p.id_peca = e.fk_peca";
        System.out.println("---------------------------");
        ResultSet lista = conn.executarConsulta(sqlConsulta);

        try {
            if (lista != null) {
                while (lista.next()) {
                    int id = lista.getInt("p.id_peca");
                    String descricaoPeca = lista.getString("p.desc_peca");
                    int quantidade = lista.getInt("e.quantidade");

                    System.out.println("ID: " + id);
                    System.out.println("Pe�a: " + descricaoPeca);
                    System.out.println("Quantidade: " + quantidade);
                    System.out.println("---------------------------");
                }
            } else {
                System.out.println("\tNenhum cliente cadastrado");
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

    public boolean apagarPeca(int idPeca) {
        boolean resposta = false;
        
        String sqlDelEstoque = "DELETE from estoque WHERE fk_peca = " + idPeca; // Primeiro apago o registro da tabela estoque
        resposta = conn.executar(sqlDelEstoque);

        if (resposta) {
            String sqlDelPeca = "DELETE from peca WHERE id_peca = " + idPeca; //se deu certo, apaga da tabela das pe�as
            resposta = conn.executar(sqlDelPeca);
            if (resposta) {
                conn.desconectar();
                return true;
            } else {
                System.out.println("Erro ao apagar a pe�a da tabela 'peca'.");
                conn.desconectar();
                return false;
            }
        } else {
            System.out.println("Erro ao apagar a pe�a da tabela 'estoque'.");
            conn.desconectar();
            return false;
        }
    }


// -------------- M�TODOS DE APOIO ---------------    
    public int validaID(int id) {
        int resposta = 0;
        try {
            String sql = "SELECT * from peca where id_peca = " + id + "";
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
        String sqlConsulta = "SELECT p.id_peca, p.desc_peca, e.quantidade from peca p join estoque e on p.id_peca = e.fk_peca";
        System.out.println("\nID | DESCRI��O | QUANTIDADE");
        ResultSet lista = conn.executarConsulta(sqlConsulta);

        try {
            while (lista.next()) {
                int id = lista.getInt("id_peca");
                String descricaoPeca = lista.getString("p.desc_peca");
                int quantidade = lista.getInt("e.quantidade");

                System.out.printf("%d. %s  \t-  \t%d\n", id, descricaoPeca, quantidade);
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

    public int verificaRegistro() {
        String sqlConsulta = "SELECT * from peca";
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
