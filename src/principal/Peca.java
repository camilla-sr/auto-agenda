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
        System.out.print("Digite o nome da pe�a:  ");
        setDescricaoPeca(sc.nextLine());
        Integer numeroValidado = null;

        while (numeroValidado == null) {
            System.out.print("Informe a quantidade: ");
            String qntd = sc.nextLine();

            // atualizo minha vari�vel para testar outra vez
            numeroValidado = h.isNumeric(qntd);

            if (numeroValidado == null) {
                System.out.println("Quantidade inv�lida. Digite novamente.");
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
        System.out.print("Digite o c�digo da pe�a que deseja editar: ");
        String pecaID = sc.nextLine();

        numeroValidado = h.isNumeric(pecaID);

        if (numeroValidado == null) {
            System.out.println("Digite apenas n�meros.");
        }

        // Verifica se o c�digo da pe�a � v�lido usando o m�todo validarPeca
        setIdPeca(numeroValidado);
        if (validarPeca() == false) {
            System.out.println("C�digo da pe�a inv�lido. Tente novamente.");
            edPeca();
        }

        // Pergunta ao usu�rio o que ele deseja editar
        System.out.println("O que voc� deseja editar?");
        System.out.println("1. Apenas a descri��o\t2. Apenas a quantidade\t3. Os dois");
        int opcaoEdicao = num.nextInt();

        switch (opcaoEdicao) {
            case 1:
                // Se o usu�rio escolher editar apenas a descri��o
                System.out.print("Digite a nova descri��o da pe�a: ");
                setDescricaoPeca(sc.nextLine());
                setQntdPeca(0);
                ed = pc.editarPeca(getIdPeca(), getDescricaoPeca(), getQntdPeca());
                break;

            case 2:
                // Se o usu�rio escolher editar apenas a quantidade
                while (numeroValidado == null) {
                    System.out.print("Informe a nova quantidade: ");
                    String qntd = sc.nextLine();

                    numeroValidado = h.isNumeric(qntd);

                    if (numeroValidado == null) {
                        System.out.println("Quantidade inv�lida. Digite novamente.");
                    }
                }
                setDescricaoPeca("");
                setQntdPeca(numeroValidado);
                ed = pc.editarPeca(getIdPeca(), getDescricaoPeca(), getQntdPeca());
                break;

            case 3:
                // Se o usu�rio escolher editar ambos
                System.out.print("Digite a nova descri��o da pe�a: ");
                setDescricaoPeca(sc.nextLine());

                numeroValidado = null;
                while (numeroValidado == null) {
                    System.out.print("Informe a nova quantidade: ");
                    String qntd = sc.nextLine();

                    // Atualiza a vari�vel para testar a entrada novamente
                    numeroValidado = h.isNumeric(qntd);

                    if (numeroValidado == null) {
                        System.out.println("Quantidade inv�lida. Digite novamente.");
                    }
                }
                setQntdPeca(numeroValidado);
                ed = pc.editarPeca(getIdPeca(), getDescricaoPeca(), getQntdPeca());
                break;

            default:
                System.out.println("Op��o inv�lida. Tente novamente.");
                edPeca();
        }

        if (ed == false) {
            System.out.println("Erro ao editar a pe�a.");
        } else {
            System.out.println("Edi��o realizada com sucesso.");
        }
    }

    public void consPeca() {
        pc.listarPecas();
    }

    public void delPeca() {
        boolean del = false;
        Integer numeroValidado = null;

        while (numeroValidado == null) {
            System.out.print("Digite o c�digo da pe�a que deseja editar: ");
            String pecaID = sc.nextLine();
            
            // atualizo minha vari�vel para testar outra vez
            numeroValidado = h.isNumeric(pecaID);

            if (numeroValidado == null) {
                System.out.println("ID inv�lido. Digite novamente.");
            }
        }

        // verifico se o id � v�lido no banco
        setIdPeca(numeroValidado);
        if (!validarPeca()) {
            System.out.println("ID n�o encontrado. Tente novamente.");
        }

        del = pc.apagarPeca(getIdPeca());
        if (del == false) {
            System.out.println("Erro ao apagar a pe�a.");
        } else {
            System.out.println("Pe�a exclu�da.");
        }
    }

    // -------------- M�TODOS DE APOIO --------------
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
