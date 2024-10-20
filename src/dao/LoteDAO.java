package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoteDAO {
    final Conexao conn = new Conexao();
    private String codLote;
    private String dataCompra;
    private String dataVencimento;
    private String tipoOleo;
    private int qntdGarrafa;

    public void cadastrarLote(String codLote, String dataCompra, String dataVencimento, int qntdGarrafa, String tipoOleo) {
        int loteValido = validaCOD(codLote);

        if (loteValido == 1) {
            System.out.println("Lote já cadastrado");
        } else {
            String sqlInserir = "INSERT into lote(cod_lote, data_compra, data_vencimento, und_lote, tipo_oleo) VALUES ('"
            +codLote+"', '"+dataCompra+"', '"+dataVencimento+"', "+qntdGarrafa+", '"+tipoOleo+"')";
            
            boolean resposta = conn.executar(sqlInserir);
            if (resposta == true) {
                System.out.println("Lote cadastrado");
            } else {
                System.out.println("Algo deu errado");
            }
        }
        conn.desconectar();
    }

    public void editarLote(String codLote, String dataCompra, String dataVencimento, int qntdGarrafa, String tipoOleo) {
        int loteValido = validaCOD(codLote);
        if (loteValido == 2) {
            System.out.println("Lote não encontrado");
        } else {
            String sqlEdit = "UPDATE lote set data_compra = '"+dataCompra+"', data_vencimento = '"+dataVencimento
                    +"', und_lote = "+qntdGarrafa+", tipo_oleo = '"+tipoOleo+"' where cod_lote = '"+codLote+"'";
            
            boolean resposta = conn.executar(sqlEdit);
            if (resposta == true) {
                System.out.println("Lote Editado");
            } else {
                System.out.println("Algo deu errado");
            }
        }
        conn.desconectar();
    }

    public void listarLote() {
        String sqlConsulta = "SELECT * from lote";
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        try {
            while (lista.next()) {
                setCodLote(lista.getString("cod_lote"));
                setDataCompra(lista.getString("data_compra"));
                setDataVencimento(lista.getString("data_vencimento"));
                setQntdGarrafa(lista.getInt("und_lote"));
                setTipoOleo(lista.getString("tipo_oleo"));

                System.out.println("Código do lote: \t\t" + getCodLote());
                System.out.println("Data da Compra: \t\t" + getDataCompra());
                System.out.println("Data de Vencimento: \t\t" + getDataVencimento());
                System.out.println("Quantidade de garrafas: \t" + getQntdGarrafa());
                System.out.println("Tipo de Óleo: \t\t\t" + getTipoOleo());
                System.out.println("---------------------------");
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

    public void apagarLote(String codLote) {
        int loteValido = validaCOD(codLote);
        if (loteValido == 2) {
            System.out.println("Lote não encontrado na base");
        } else {
            String sqlDel = "DELETE from lote where cod_lote = '"+codLote+"'";
            boolean resposta = conn.executar(sqlDel);
            if (resposta == true) {
                System.out.println("Lote deletado");
            } else {
                System.out.println("Algo deu errado");
            }
        }
        conn.desconectar();
    }
    
    // -------------- MÉTODOS DE APOIO --------------
    
    private int validaCOD(String codLote) {
        int resposta = 0;
        try {
            String sql = "SELECT * from lote where cod_lote = '"+ codLote +"'";
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
    public String getCodLote() {
        return codLote;
    }
    public void setCodLote(String codLote) {
        this.codLote = codLote;
    }
    public String getDataCompra() {
        return dataCompra;
    }
    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }
    public String getDataVencimento() {
        return dataVencimento;
    }
    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
    public String getTipoOleo() {
        return tipoOleo;
    }
    public void setTipoOleo(String tipoOleo) {
        this.tipoOleo = tipoOleo;
    }
    public int getQntdGarrafa() {
        return qntdGarrafa;
    }
    public void setQntdGarrafa(int qntdGarrafa) {
        this.qntdGarrafa = qntdGarrafa;
    }
    // ------------------------------------------------
}
