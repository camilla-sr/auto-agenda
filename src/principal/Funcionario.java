package principal;

import dao.FuncionarioDAO;
import include.Helper;
import java.util.Scanner;

public class Funcionario {
    Integer numeroValidado = null;
    FuncionarioDAO f = new FuncionarioDAO();
    Scanner sc = new Scanner(System.in, "utf8");
    Helper h = new Helper();

    private int idFuncionario;
    private String nomeFuncionario;

    public void addFuncionario() {
        boolean cad = false;

        System.out.println("\nCadastrando novo funcionário");
        System.out.print("Nome: ");
        setNomeFuncionario(sc.nextLine());

        cad = f.cadastrarFuncionario(getNomeFuncionario());
        if (cad == false) {
            System.out.println("Algo deu errado");
        } else {
            System.out.println("Cadastro realizado com sucesso.");
        }
    }

    public void edFuncionario() {
        numeroValidado = null;
        boolean ed = false;

        f.listaEdicao();
        while (numeroValidado == null) {
            System.out.print("Digite o id do funcionário: ");
            String funcionarioID = sc.nextLine();

            // Valida se a entrada é numérica
            numeroValidado = h.isNumeric(funcionarioID);

            if (numeroValidado == null) {
                System.out.println("Apenas números.");
            } else {
                // Define o ID da peça e verifica a existência
                setIdFuncionario(numeroValidado);

                if (!validarFuncionario()) {
                    System.out.println("ID não encontrado.");
                    numeroValidado = null; // Redefine para continuar o loop
                } else {
                    System.out.println("ID válido encontrado.");
                }
            }
        }

        // Pergunta ao usuário o que ele deseja editar
        System.out.print("Novo nome: ");
        setNomeFuncionario(sc.nextLine());

        ed = f.editarFuncionario(getIdFuncionario(), getNomeFuncionario());
        if (!ed) {
            System.out.println("Erro ao editar a peça.");
        } else {
            System.out.println("Edição realizada com sucesso.");
        }
    }

    public void consFuncionario() {
        f.listarFuncionarios();
    }

    public void delFuncionario() {
        boolean del = false;
        numeroValidado = null;

        f.listaEdicao();
        while (numeroValidado == null) {
            System.out.print("Digite o ID do funcionário: ");
            String funcionarioID = sc.nextLine();

            // atualizo minha variável para testar outra vez
            numeroValidado = h.isNumeric(funcionarioID);

            if (numeroValidado == null) {
                System.out.println("Digite apenas números.");
            }
        }

        // verifico se o id é válido no banco
        setIdFuncionario(numeroValidado);
        if (!validarFuncionario()) {
            System.out.println("ID não encontrado");
            numeroValidado = null;  // Redefine para continuar o loop
        }

        del = f.apagarFuncionario(getIdFuncionario());
        if (del == false) {
            System.out.println("Erro ao apagar funcionário.");
        } else {
            System.out.println("Peça excluída.");
        }
    }

// -------------- MÉTODOS DE APOIO --------------
    public boolean validarFuncionario() {
        if (f.validaID(getIdFuncionario()) == 1) {
            return true;
        } else {
            return false;
        }
    }

// -------------- GETTERS E SETTERS --------------
    public int getIdFuncionario() {
        return idFuncionario;
    }
    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
    public String getNomeFuncionario() {
        return nomeFuncionario;
    }
    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }
// ------------------------------------------------    
}
