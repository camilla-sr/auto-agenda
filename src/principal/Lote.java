package principal;

import include.Helper;
import java.util.Scanner;
import dao.LoteDAO;

public class Lote {
    Integer numeroValidado = null;
    Helper h = new Helper();
    Scanner sc = new Scanner(System.in, "utf8");
    Scanner num = new Scanner(System.in);
    LoteDAO lt = new LoteDAO();

    private String codLote;
    private String dataCompra;
    private String dataVencimento;
    private String tipoOleo;
    private int qntdGarrafa;

    public void addLote() {
        boolean cad = false;

        System.out.print("C�digo do lote:  ");
        setCodLote(sc.nextLine());

        System.out.print("Data de compra (formato: dd/MM/yyyy): ");
        String dataC = sc.nextLine();
        setDataCompra(h.dataPadraoBanco(dataC));

        System.out.print("Data de vencimento (formato: dd/MM/yyyy): ");
        String dataV = sc.nextLine();
        setDataVencimento(h.dataPadraoBanco(dataV));

        System.out.print("Tipo de �leo: ");
        setTipoOleo(sc.nextLine());

        while (numeroValidado == null) {
            System.out.print("Quantidade de garrafas: ");
            String qntd = sc.nextLine();

            numeroValidado = h.isNumeric(qntd);

            if (numeroValidado == null) {
                System.out.println("Quantidade inv�lida. Digite novamente.");
            }
        }

        setQntdGarrafa(numeroValidado);
        cad = lt.cadastrarLote(getCodLote(), getDataCompra(), getDataVencimento(), getQntdGarrafa(), getTipoOleo());
        if (cad == false) {
            System.out.println("Algo deu errado");
        } else {
            System.out.println("Cadastro realizado");
        }
    }

    public void edLote() {
        boolean ed = false;
        System.out.print("Digite o c�digo do lote que deseja editar: ");
        String loteID = sc.nextLine();
        setCodLote(loteID);  // Define o loteID no objeto para a primeira chamada de valida��o

        // Repete at� encontrar um lote v�lido
        while (validarLote() == false) {
            System.out.println("Lote n�o encontrado. Tente novamente.");
            loteID = sc.nextLine();
            setCodLote(loteID);  // Atualiza o loteID antes da pr�xima valida��o
        }

        System.out.println("O que voc� deseja editar?");
        System.out.println("1. Tipo de �leo\t\t 2. Quantidade de garrafas"
                + "\n3. Data de compra\t 4. Data de vencimento\t 5. Descri��o e quantidade"
                + "\n6. Data de compra e data de vencimento\t\t 7. Todos os campos\n0. Voltar");

        int opcaoEdicao = num.nextInt();

        switch (opcaoEdicao) {
            case 1:
                // Editar apenas a descri��o do tipo de �leo
                System.out.print("Digite a nova descri��o do tipo de �leo: ");
                String oleo = sc.nextLine();
                setTipoOleo(oleo);
                ed = lt.editarLote(getCodLote(), getDataCompra(), getDataVencimento(), getQntdGarrafa(), getTipoOleo());
                break;

            case 2:
                // Editar apenas a quantidade de garrafas
                while (numeroValidado == null) {
                    System.out.print("Informe a nova quantidade de garrafas: ");
                    String qntd = sc.nextLine();
                    numeroValidado = h.isNumeric(qntd);

                    if (numeroValidado == null) {
                        System.out.println("Quantidade inv�lida. Digite novamente.");
                    }
                }
                setQntdGarrafa(numeroValidado);
                ed = lt.editarLote(getCodLote(), getDataCompra(), getDataVencimento(), getQntdGarrafa(), getTipoOleo());
                break;

            case 3:
                // Editar apenas a data de compra
                System.out.print("Informe a nova data de compra (dd/MM/yyyy): ");
                String novaDataCompra = sc.nextLine();
                setDataCompra(h.dataPadraoBanco(novaDataCompra));
                ed = lt.editarLote(getCodLote(), getDataCompra(), getDataVencimento(), getQntdGarrafa(), getTipoOleo());
                break;

            case 4:
                // Editar apenas a data de vencimento
                System.out.print("Informe a nova data de vencimento (dd/MM/yyyy): ");
                String novaDataVencimento = sc.nextLine();
                setDataVencimento(h.dataPadraoBanco(novaDataVencimento));
                ed = lt.editarLote(getCodLote(), getDataCompra(), getDataVencimento(), getQntdGarrafa(), getTipoOleo());
                break;

            case 5:
                // Editar ambos: descri��o do tipo de �leo e quantidade
                System.out.print("Digite a nova descri��o do tipo de �leo: ");
                setTipoOleo(sc.nextLine());

                numeroValidado = null;
                while (numeroValidado == null) {
                    System.out.print("Informe a nova quantidade de garrafas: ");
                    String qntd = sc.nextLine();
                    numeroValidado = h.isNumeric(qntd);
                    if (numeroValidado == null) {
                        System.out.println("Quantidade inv�lida. Digite novamente.");
                    }
                }
                setQntdGarrafa(numeroValidado);
                ed = lt.editarLote(getCodLote(), getDataCompra(), getDataVencimento(), getQntdGarrafa(), getTipoOleo());
                break;

            case 6:
                // Editar ambas as datas
                System.out.print("Informe a nova data de compra (dd/MM/yyyy): ");
                novaDataCompra = sc.nextLine();
                setDataCompra(h.dataPadraoBanco(novaDataCompra));

                System.out.print("Informe a nova data de vencimento (dd/MM/yyyy): ");
                novaDataVencimento = sc.nextLine();
                setDataVencimento(h.dataPadraoBanco(novaDataVencimento));

                ed = lt.editarLote(getCodLote(), getDataCompra(), getDataVencimento(), getQntdGarrafa(), getTipoOleo());
                break;

            case 7:
                // Editar todos os campos
                System.out.print("Digite a nova descri��o do tipo de �leo: ");
                setTipoOleo(sc.nextLine());

                numeroValidado = null;
                while (numeroValidado == null) {
                    System.out.print("Informe a nova quantidade de garrafas: ");
                    String qntd = sc.nextLine();
                    numeroValidado = h.isNumeric(qntd);
                    if (numeroValidado == null) {
                        System.out.println("Quantidade inv�lida. Digite novamente.");
                    }
                }
                setQntdGarrafa(numeroValidado);

                System.out.print("Informe a nova data de compra (dd/MM/yyyy): ");
                novaDataCompra = sc.nextLine();
                setDataCompra(h.dataPadraoBanco(novaDataCompra));

                System.out.print("Informe a nova data de vencimento (dd/MM/yyyy): ");
                novaDataVencimento = sc.nextLine();
                setDataVencimento(h.dataPadraoBanco(novaDataVencimento));

                ed = lt.editarLote(getCodLote(), getDataCompra(), getDataVencimento(), getQntdGarrafa(), getTipoOleo());
                break;

            case 0:
                break;
            default:
                System.out.println("Op��o inv�lida. Tente novamente.");
                return;
        }

        if (ed == false) {
            System.out.println("Erro ao editar o lote.");
        } else {
            System.out.println("Edi��o realizada com sucesso.");
        }
    }

    public void consLote() {
        lt.listarLote();
    }

    public void delLote() {
        boolean del = false;
        System.out.print("Digite o c�digo do lote que deseja editar: ");
        String loteID = sc.nextLine();
        setCodLote(loteID);

        while (validarLote() == false) {
            System.out.println("Lote n�o encontrado. Tente novamente.");
            String loteCod = sc.nextLine();
            setCodLote(loteCod);  // Atualiza o loteID antes da pr�xima valida��o
        }

        del = lt.apagarLote(getCodLote());
        if (del == false) {
            System.out.println("Erro ao apagar o lote.");
        } else {
            System.out.println("Lote exclu�do com sucesso.");
        }
    }

    // -------------- M�TODOS DE APOIO --------------
    public boolean validarLote() {
        if (lt.validaCOD(getCodLote()) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public String mudaOrdemData(String data) {
        String[] dataPartes = data.split("/"); //separo o dado obtido no input
        if (dataPartes.length == 3) {
            return dataPartes[2] + "-" + dataPartes[1] + "-" + dataPartes[0]; // yyyy/MM/dd
        } else {
            System.out.println("Formato de data inv�lido");
            return null;
        }
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