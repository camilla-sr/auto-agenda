package br.com.autoagenda.autoagenda.model;

import br.com.autoagenda.autoagenda.include.Conexao;

public class M_Funcionario {
    private int idFuncionario;
    private String nomeFuncionario;
    private String usuario;
    private String senha;
	
	public M_Funcionario() {}

	public M_Funcionario(int idFuncionario, String nomeFuncionario, String usuario, String senha) {
		this.idFuncionario = idFuncionario;
		this.nomeFuncionario = nomeFuncionario;
		this.usuario = usuario;
		this.senha = senha;
	}
    
	public M_Funcionario logar(String usuario, String senha) {
		M_Funcionario func = Conexao.logar(usuario, senha);
        if (func == null) return null;
        return func;
	}
    
    public boolean cadastrarFuncionario(String nomeFuncionario, String usuario, String senha) {
        String sql = "INSERT INTO funcionario (nome_funcionario) VALUES ('" + nomeFuncionario + "', '" + usuario + "', "
        		+ "'" + senha + "')";
        
        boolean resposta = Conexao.executar(sql);
        if(!resposta) return false;
        return true;
    }

//    public void listarFuncionarios() {
//        String sqlConsulta = "SELECT * FROM funcionario order by nome_funcionario";
//        ResultSet lista = conn.executarConsulta(sqlConsulta);
//        try {
//            if (lista != null) {
//                System.out.println("\n\tListando Funcionários");
//                while (lista.next()) {
//                    int idFuncionario = lista.getInt("id_funcionario");
//                    String nomeFuncionario = lista.getString("nome_funcionario");
//
//                    System.out.println(nomeFuncionario);
//                }
//            } else {
//                System.out.println("\tNenhum funcinonário cadastrado");
//            }
//            System.out.println("---------------------------");
//            lista.close();
//        } catch (SQLException e) {
//            System.out.println("Erro ao processar resultado: " + e.getMessage());
//        } finally {
//            conn.desconectar();
//        }
//    }
//
//    public boolean editarFuncionario(int idFuncionario, String novoNome) {
//        boolean resposta = false;
//        boolean funcionarioValido = validaID(idFuncionario);
//
//        if (!funcionarioValido) {
//            System.out.println("Funcionário não encontrado na base");
//        } else {
//            String sqlEdit = "UPDATE funcionario set nome_funcionario = '" + novoNome + "' where id_funcionario = " + idFuncionario + "";
//            resposta = conn.executar(sqlEdit);
//        }
//        if (resposta == true) {
//            conn.desconectar();
//            return true;
//        } else {
//            conn.desconectar();
//            return false;
//        }
//    }
//
//    public boolean apagarFuncionario(int idFuncionario) {
//        boolean resposta = false;
//        boolean funcionarioValido = validaID(idFuncionario);
//
//        if (!funcionarioValido) {
//            System.out.println("Funcionário não encontrado na base");
//        } else {
//            String sqlDel = "DELETE from funcionario where id_funcionario = " + idFuncionario;
//            resposta = conn.executar(sqlDel);
//        }
//        if (resposta == true) {
//            conn.desconectar();
//            return true;
//        } else {
//            conn.desconectar();
//            return false;
//        }
//    }

// -------------- MÉTODOS DE APOIO --------------
    
    public int getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(int idFuncionario) { this.idFuncionario = idFuncionario; }
    public String getNomeFuncionario() { return nomeFuncionario; }
    public void setNomeFuncionario(String nomeFuncionario) { this.nomeFuncionario = nomeFuncionario; }
	public String getUsuario() { return usuario; }
	public void setUsuario(String usuario) { this.usuario = usuario; }
	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }
}
