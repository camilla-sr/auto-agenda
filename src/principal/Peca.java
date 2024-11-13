package principal;

import dao.PecaDAO;
import include.Helper;
import java.util.Scanner;

public class Peca {
    Integer numeroValidado = null;
    Helper h = new Helper();
    Scanner sc = new Scanner(System.in, "utf8");
    Scanner num = new Scanner(System.in);
    PecaDAO pc = new PecaDAO();

    private int idPeca;
    private String descricaoPeca;
    private int qntdPeca;

    public void addPeca() {
        System.out.print("Digite o nome da peça:  ");
        setDescricaoPeca(sc.nextLine());
        Integer numeroValidado = null;

        while (numeroValidado == null) {
            System.out.print("Informe a quantidade: ");
            String qntd = sc.nextLine();

            // atualizo minha variável para testar outra vez
            numeroValidado = h.isNumeric(qntd);

            if (numeroValidado == null) {
                System.out.println("Quantidade inválida. Digite novamente.");
            }
        }

        setQntdPeca(numeroValidado);
        boolean cad = pc.cadastrarPeca(getDescricaoPeca(), getQntdPeca());
        if (cad == false) {
            System.out.println("Algo deu errado");
        } else {
            System.out.println("Cadastro realizado");
        }
    }

    public void edPeca() {
        boolean ed = false;
        System.out.print("Digite o código da peça que deseja editar: ");
        String pecaID = sc.nextLine();

        numeroValidado = h.isNumeric(pecaID);

        if (numeroValidado == null) {
            System.out.println("Digite apenas números.");
        }

        // Verifica se o código da peça é válido usando o método validarPeca
        setIdPeca(numeroValidado);
        if (validarPeca() == false) {
            System.out.println("Código da peça inválido. Tente novamente.");
            edPeca();
        }

        // Pergunta ao usuário o que ele deseja editar
        System.out.println("O que você deseja editar?");
        System.out.println("1. Apenas a descrição\t2. Apenas a quantidade\t3. Os dois");
        int opcaoEdicao = num.nextInt();

        switch (opcaoEdicao) {
            case 1:
                // Se o usuário escolher editar apenas a descrição
                System.out.print("Digite a nova descrição da peça: ");
                setDescricaoPeca(sc.nextLine());
                setQntdPeca(0);
                ed = pc.editarPeca(getIdPeca(), getDescricaoPeca(), getQntdPeca());
                break;

            case 2:
                // Se o usuário escolher editar apenas a quantidade
                while (numeroValidado == null) {
                    System.out.print("Informe a nova quantidade: ");
                    String qntd = sc.nextLine();

                    numeroValidado = h.isNumeric(qntd);

                    if (numeroValidado == null) {
                        System.out.println("Quantidade inválida. Digite novamente.");
                    }
                }
                setDescricaoPeca("");
                setQntdPeca(numeroValidado);
                ed = pc.editarPeca(getIdPeca(), getDescricaoPeca(), getQntdPeca());
                break;

            case 3:
                // Se o usuário escolher editar ambos
                System.out.print("Digite a nova descrição da peça: ");
                setDescricaoPeca(sc.nextLine());

                numeroValidado = null;
                while (numeroValidado == null) {
                    System.out.print("Informe a nova quantidade: ");
                    String qntd = sc.nextLine();

                    // Atualiza a variável para testar a entrada novamente
                    numeroValidado = h.isNumeric(qntd);

                    if (numeroValidado == null) {
                        System.out.println("Quantidade inválida. Digite novamente.");
                    }
                }
                setQntdPeca(numeroValidado);
                ed = pc.editarPeca(getIdPeca(), getDescricaoPeca(), getQntdPeca());
                break;

            default:
                System.out.println("Opção inválida. Tente novamente.");
                edPeca();
        }

        if (ed == false) {
            System.out.println("Erro ao editar a peça.");
        } else {
            System.out.println("Edição realizada com sucesso.");
        }
    }

    public void consPeca() {
        pc.listarPecas();
    }

    public void delPeca() {
        boolean del = false;
        Integer numeroValidado = null;

        while (numeroValidado == null) {
            System.out.print("Digite o código da peça que deseja editar: ");
            String pecaID = sc.nextLine();
            
            // atualizo minha variável para testar outra vez
            numeroValidado = h.isNumeric(pecaID);

            if (numeroValidado == null) {
                System.out.println("ID inválido. Digite novamente.");
            }
        }

        // verifico se o id é válido no banco
        setIdPeca(numeroValidado);
        if (!validarPeca()) {
            System.out.println("ID não encontrado. Tente novamente.");
        }

        del = pc.apagarPeca(getIdPeca());
        if (del == false) {
            System.out.println("Erro ao apagar a peça.");
        } else {
            System.out.println("Peça excluída.");
        }
    }

    // -------------- MÉTODOS DE APOIO --------------
    public boolean validarPeca() {
        if (pc.validaID(getIdPeca()) == 1) {
            return true;
        } else {
            return false;
        }
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
