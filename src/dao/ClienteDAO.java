package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

    final Conexao conn = new Conexao();
    private int idCliente;
    private String nomeCliente;
    private String whatsappCliente;
    private String modeloCarro;
    private String anoCarro;

    //Métodos Principais
    public void cadastrarCliente(String nomeCliente, String whatsappCliente, String modeloCarro, String anoCarro) {
        String sqlInserir = "INSERT into cliente (nome_cliente, whatsapp_cliente, modelo_carro, ano_carro)"
                + "VALUES ('" + nomeCliente + "', '" + whatsappCliente + "', '" + modeloCarro + "', '" + anoCarro + "')";

        boolean resposta = conn.executar(sqlInserir);
        if (resposta == true) {
            System.out.println("Cliente inserido");
        } else {
            System.out.println("Algo deu errado");
        }
        conn.desconectar();
    }

    public void editarCliente(int idCliente, String novoNomeCliente, String novoWhatsappCliente, String novoModeloCarro, String novoAnoCarro) {
        int clienteValida = validaID(idCliente);

        if (clienteValida == 2) {
            System.out.println("Cliente não encontrado na base");
        } else {
            String sqlEdit = "UPDATE cliente set nome_cliente = '" + novoNomeCliente + "', whatsapp_cliente = '" + novoWhatsappCliente
                    + "', modelo_carro = '" + novoModeloCarro + "', ano_carro = '" + novoAnoCarro
                    + "' WHERE id_cliente = " + idCliente + "";

            boolean resposta = conn.executar(sqlEdit);
            if (resposta == true) {
                System.out.println("Cliente editado");
            } else {
                System.out.println("Algo deu errado");
            }
        }
        conn.desconectar();
    }

    public void listarCliente() {
        String sqlConsulta = "SELECT * from cliente";
        System.out.println("---------------------------");
        ResultSet lista = conn.executarConsulta(sqlConsulta);

        try {
            while (lista.next()) {
                int id = lista.getInt("id_cliente");
                String nome = lista.getString("nome_cliente");
                String whatsapp = lista.getString("whatsapp_cliente");
                String modelo = lista.getString("modelo_carro");
                String ano = lista.getString("ano_carro");

                System.out.println("ID: " + id);
                System.out.println("Nome do cliente: " + nome);
                System.out.println("WhatsApp: " + whatsapp);
                System.out.println("Modelo do veículo: " + modelo);
                System.out.println("Ano do veículo: " + ano);
                System.out.println("---------------------------");
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

    public void apagarCliente() {
        int clienteValida = validaID(idCliente);

        if (clienteValida == 2) {
            System.out.println("Cliente não encontrado na base");
        } else {
            String sqlDel = "DELETE from cliente WHERE id_cliente = " + idCliente + "";
            boolean resposta = conn.executar(sqlDel);
            if (resposta == true) {
                System.out.println("Cliente deletado");
            } else {
                System.out.println("Algo deu errado");
            }
        }
        conn.desconectar();
    }

// -------------- MÉTODO DE APOIO ---------------- 
    private int validaID(int id) {
        int resposta = 0;
        try {
            String sql = "SELECT * from cliente WHERE id_cliente = " + id + "";
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
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getWhatsappCliente() {
        return whatsappCliente;
    }

    public void setWhatsappCliente(String whatsappCliente) {
        this.whatsappCliente = whatsappCliente;
    }

    public String getModeloCarro() {
        return modeloCarro;
    }

    public void setModeloCarro(String modeloCarro) {
        this.modeloCarro = modeloCarro;
    }

    public String getAnoCarro() {
        return anoCarro;
    }

    public void setAnoCarro(String anoCarro) {
        this.anoCarro = anoCarro;
    }
// ------------------------------------------------
}
