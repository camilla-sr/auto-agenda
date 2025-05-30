package principal;

import dao.PecaDAO;
import include.Helper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Peca {
    LocalDateTime agora = LocalDateTime.now(); // Retorna data e hora atuais com base no servidor
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String dataHoje = agora.format(formato);
    
    Integer numeroValidado = null;
    Helper h = new Helper();
    Scanner sc = new Scanner(System.in, "utf8");
    Scanner num = new Scanner(System.in);
    PecaDAO pc = new PecaDAO();

    private int idPeca;
    private String descricaoPeca;
    private int qntdPeca;

    public void addPeca() {
        System.out.println("\n\tCadastro de novas pe�as\n");
        System.out.print("Nome da pe�a: ");
        String peca = sc.nextLine();

        while (peca.isEmpty()) {
            System.out.println("Entrada vazia. Tente novamente\n");
            System.out.print("Nome da pe�a: ");
            peca = sc.nextLine();
        }

        // Define a descri��o da pe�a ap�s validar a entrada
        setDescricaoPeca(peca);
        numeroValidado = null;

        while (numeroValidado == null) {
            System.out.print("Quantidade: ");
            String qntd = sc.nextLine();

            // atualizo minha vari�vel para testar outra vez
            numeroValidado = h.isNumeric(qntd);

            if (numeroValidado == null) {
                System.out.println("Digite apenas n�meros.");
            }
        }

        setQntdPeca(numeroValidado);
        boolean cad = pc.cadastrarPeca(getDescricaoPeca(), getQntdPeca(), dataHoje);
        if (cad == false) {
            System.out.println("Algo deu errado");
        } else {
            System.out.println("Cadastro realizado");
        }
    }

    public void edPeca() {
        numeroValidado = null; // Reinicializa a vari�vel
        boolean ed = false;

        if (pc.verificaRegistro() == 0) {
            System.out.println("\nN�o h� pe�as cadastrados");
            System.out.println("Retornando para o menu principal.");
            return;
        } else {
            pc.listaEdicao();
        }

        while (numeroValidado == null) {
            System.out.print("Digite o id da pe�a: ");
            String pecaID = sc.nextLine();

            // Valida se a entrada � num�rica
            numeroValidado = h.isNumeric(pecaID);

            if (numeroValidado == null) {
                System.out.println("Apenas n�meros.");
            } else {
                // Define o ID da pe�a e verifica a exist�ncia
                setIdPeca(numeroValidado);

                if (!validarPeca()) {
                    System.out.println("ID n�o encontrado.");
                    numeroValidado = null; // Redefine para continuar o loop
                } else {
                    System.out.println("ID v�lido encontrado.");
                }
            }
        }

        // Pergunta ao usu�rio o que ele deseja editar
        System.out.print("\n\n1. Apenas a descri��o\t2. Apenas a quantidade\t3. Os dois\n0. Voltar");
        System.out.print("\nO que voc� deseja editar? >>>>>  ");
        int opcaoEdicao = num.nextInt();

        switch (opcaoEdicao) {
            case 0:
                if (opcaoEdicao == 0) {
                    System.out.println("Opera��o cancelada");
                    return;
                }
                break;
            case 1:
                System.out.print("Digite a nova descri��o da pe�a: ");
                setDescricaoPeca(sc.nextLine());
                setQntdPeca(0);
                ed = pc.editarPeca(getIdPeca(), getDescricaoPeca(), getQntdPeca(), dataHoje);
                break;
            case 2:
                numeroValidado = null; // Reinicializa para validar a quantidade
                while (numeroValidado == null) {
                    System.out.print("Informe a nova quantidade: ");
                    String qntd = sc.nextLine();

                    numeroValidado = h.isNumeric(qntd);

                    if (numeroValidado == null) {
                        System.out.println("Quantidade inv�lida. Digite novamente.");
                    }
                }
                setQntdPeca(numeroValidado);
                ed = pc.editarPeca(getIdPeca(), getDescricaoPeca(), getQntdPeca(), dataHoje);
                break;
            case 3:
                System.out.print("Digite a nova descri��o da pe�a: ");
                setDescricaoPeca(sc.nextLine());

                numeroValidado = null;
                while (numeroValidado == null) {
                    System.out.print("Informe a nova quantidade: ");
                    String qntd = sc.nextLine();

                    numeroValidado = h.isNumeric(qntd);

                    if (numeroValidado == null) {
                        System.out.println("Quantidade inv�lida. Digite novamente.");
                    }
                }
                setQntdPeca(numeroValidado);
                ed = pc.editarPeca(getIdPeca(), getDescricaoPeca(), getQntdPeca(), dataHoje);
                break;
            default:
                System.out.println("Op��o inv�lida. Tente novamente.");
                edPeca();
        }
        if (!ed) {
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
        numeroValidado = null;

        if (pc.verificaRegistro() == 0) {
            System.out.println("\nN�o h� pe�as cadastrados");
            System.out.println("Retornando para o menu principal.");
            return;
        } else {
            pc.listaEdicao();
        }

        while (numeroValidado == null || validarPeca() == false) {
            System.out.print("Digite o c�digo da pe�a: ");
            String pecaID = sc.nextLine();

            // atualizo minha vari�vel para testar outra vez
            numeroValidado = h.isNumeric(pecaID);

            if (numeroValidado == null) {
                System.out.println("Digite apenas n�meros.");
            } else {
                // verifico se o id � v�lido no banco
                setIdPeca(numeroValidado);
                if (!validarPeca()) {
                    System.out.println("ID n�o encontrado. Tente novamente.");
                    numeroValidado = null;  // Redefine para continuar o loop
                }
            }
        }
        del = pc.apagarPeca(getIdPeca());
        if (del == false) {
            System.out.println("Dado em uso, n�o � poss�vel excuir");
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
