package principal;

import dao.FuncionarioDAO;
import include.Helper;
import java.util.Scanner;

public class Funcionario {
    Integer numeroValidado = null;
    FuncionarioDAO f = new FuncionarioDAO();
    Scanner sc = new Scanner(System.in, "utf8");
    Scanner num = new Scanner(System.in);
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
        
        if (f.listaEdicao() == 0) {
            System.out.println("\t\t\tNenhum dado encontrado");
            return;
        }
        
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
                }
            }
        }
        
        System.out.print("\n1. Editar nome do Funcionário\t0. Voltar");
        System.out.print("\nComo você deseja prosseguir?  >>>>");

        int opcaoEdicao = num.nextInt();

        switch (opcaoEdicao) {
            case 0:
                if(opcaoEdicao == 0){
                    System.out.println("Operaçãoo cancelada");
                    return;
                }
                break;
            case 1:
                // Pergunta ao usuário o que ele deseja editar
                System.out.print("Novo nome: ");
                setNomeFuncionario(sc.nextLine());

                ed = f.editarFuncionario(getIdFuncionario(), getNomeFuncionario());
                
                if (!ed) {
                    System.out.println("Erro ao editar a funcionário.");
                } else {
                    System.out.println("Edição realizada com sucesso.");
                }
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                edFuncionario();
        }
    }

    public void consFuncionario() {
        f.listarFuncionarios();
    }

    public void delFuncionario() {
        boolean del = false;
        numeroValidado = null;
        
        if (f.listaEdicao() == 0) {
            System.out.println("\t\t\tNenhum dado encontrado");
            return;
        }
        
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
            System.out.println("Dado em uso, não é possível excuir");
        } else {
            System.out.println("Funcionário excluído");
        }
    }

// -------------- MÉTODOS DE APOIO --------------
    public boolean validarFuncionario() {
        if (f.validaID(getIdFuncionario())) {
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
