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
        
        System.out.print("\nCódigo do lote:  ");
        setCodLote(sc.nextLine());

        while(true){
            System.out.print("Data de compra (formato: dd/MM/yyyy): ");
            String dataC = sc.nextLine();

            String dataValida = h.dataPadraoBanco(dataC);
            if(dataValida != null){
                setDataCompra(dataValida);
                break;
            }
        }
        while(true){ 
            System.out.print("Data de vencimento (formato: dd/MM/yyyy): ");
            String dataV = sc.nextLine();
        
            String dataValida = h.dataPadraoBanco(dataV);
            if (dataValida != null) {
                setDataVencimento(dataValida);
                break;
            }
        }
        System.out.print("Tipo de óleo: ");
        setTipoOleo(sc.nextLine());

        while (numeroValidado == null) {
            System.out.print("Quantidade de garrafas: ");
            String qntd = sc.nextLine();

            numeroValidado = h.isNumeric(qntd);

            if (numeroValidado == null) {
                System.out.println("Quantidade inválida. Digite novamente.");
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
        setCodLote("");
        setDataCompra("");
        setDataVencimento("");
        setQntdGarrafa(0);
        setTipoOleo("");
        
        boolean ed = false;
        
        lt.listaEdicao();
        System.out.print("\nDigite o código do lote que deseja editar: ");
        String loteID = sc.nextLine();
        setCodLote(loteID);  // Define o loteID no objeto para a primeira chamada de validação

        // Repete até encontrar um lote válido
        while (validarLote() == false) {
            System.out.println("Lote não encontrado. Tente novamente.");
            loteID = sc.nextLine();
            setCodLote(loteID);  // Atualiza o loteID antes da próxima validação
        }
        
        System.out.println("\n1. Tipo de óleo\t\t 2. Quantidade de garrafas \t3. Data de compra"
                + "\n4. Data de vencimento\t 5. Tipo óleo e quantidade\t6. Datas de compra e vencimento"
                + "\n7. Todos os campos\t 0. Voltar");
        System.out.print("O que você deseja editar? >>>>> ");
        int opcaoEdicao = num.nextInt();
        System.out.println("");

        switch (opcaoEdicao) {
            case 1:
                // Editar apenas a descrição do tipo de óleo
                System.out.print("Digite a nova descrição do tipo de óleo: ");
                String oleo = sc.nextLine();
                setTipoOleo(oleo);
                ed = lt.editarLote(getCodLote(), getDataCompra(), getDataVencimento(), getQntdGarrafa(), getTipoOleo());
                break;

            case 2:
                Integer qntdValida = null;
                // Editar apenas a quantidade de garrafa
                while (qntdValida == null){
                    System.out.print("Informe a nova quantidade de garrafas: ");
                    String qntd = sc.nextLine();
                    qntdValida = h.isNumeric(qntd);

                    if (qntdValida == null) {
                        System.out.println("Quantidade inválida. Digite novamente.");
                    }
                }
                setQntdGarrafa(qntdValida);
                ed = lt.editarLote(getCodLote(), getDataCompra(), getDataVencimento(), getQntdGarrafa(), getTipoOleo());
                break;

            case 3:
                // Editar apenas a data de compra com validação
                while(true){
                    System.out.print("Informe a nova data de compra (dd/MM/yyyy): ");
                    String novaDataCompra = sc.nextLine();

                    String dataValida = h.dataPadraoBanco(novaDataCompra);
                    
                    if(dataValida != null){
                        setDataCompra(dataValida);
                        break; // Sai do loop se a data for válida
                    }
                }
                ed = lt.editarLote(getCodLote(), getDataCompra(), getDataVencimento(), getQntdGarrafa(), getTipoOleo());
                break;
                
            case 4:
                // Editar apenas a data de vencimento com validação
                while(true){
                    System.out.print("Informe a nova data de vencimento (dd/MM/yyyy): ");
                    String novaDataVencimento = sc.nextLine();

                    String dataValida = h.dataPadraoBanco(novaDataVencimento);
                    
                    if(dataValida != null){
                        setDataVencimento(dataValida);
                        break; // Sai do loop se a data for válida    
                    }
                }
                ed = lt.editarLote(getCodLote(), getDataCompra(), getDataVencimento(), getQntdGarrafa(), getTipoOleo());
                break;

            case 5:
                // Editar ambos: descrição do tipo de óleo e quantidade
                System.out.print("Digite a nova descrição do tipo de óleo: ");
                setTipoOleo(sc.nextLine());

                numeroValidado = null;
                while (numeroValidado == null) {
                    System.out.print("Informe a nova quantidade de garrafas: ");
                    String qntd = sc.nextLine();
                    numeroValidado = h.isNumeric(qntd);
                    if (numeroValidado == null) {
                        System.out.println("Quantidade inválida. Digite novamente.");
                    }
                }
                setQntdGarrafa(numeroValidado);
                ed = lt.editarLote(getCodLote(), getDataCompra(), getDataVencimento(), getQntdGarrafa(), getTipoOleo());
                break;

            case 6:
                // Editar ambas as datas com validação
                while(true){
                    System.out.print("Informe a nova data de compra (dd/MM/yyyy): ");
                    String novaDataCompra = sc.nextLine();

                    String dataValidaC = h.dataPadraoBanco(novaDataCompra);
                    
                    if(dataValidaC != null){
                        setDataCompra(dataValidaC);
                        break; // Sai do loop se a data for válida
                    }
                }

                 while(true){
                    System.out.print("Informe a nova data de vencimento (dd/MM/yyyy): ");
                    String novaDataVencimento = sc.nextLine();

                    String dataValidaV = h.dataPadraoBanco(novaDataVencimento);
                    
                    if(dataValidaV != null){
                        setDataVencimento(dataValidaV);
                        break; // Sai do loop se a data for válida    
                    }
                }
            ed = lt.editarLote(getCodLote(), getDataCompra(), getDataVencimento(), getQntdGarrafa(), getTipoOleo());
            break;

            case 7:
                // Editar todos os campos
                System.out.print("Digite a nova descrição do tipo de óleo: ");
                setTipoOleo(sc.nextLine());

                numeroValidado = null;
                while (numeroValidado == null) {
                    System.out.print("Informe a nova quantidade de garrafas: ");
                    String qntd = sc.nextLine();
                    numeroValidado = h.isNumeric(qntd);
                    if (numeroValidado == null) {
                        System.out.println("Quantidade inválida. Digite novamente.");
                    }
                }
                setQntdGarrafa(numeroValidado);

                while(true){
                    System.out.print("Informe a nova data de compra (dd/MM/yyyy): ");
                    String novaDataCompra = sc.nextLine();

                    String dataValidaCompra = h.dataPadraoBanco(novaDataCompra);
                    
                    if(dataValidaCompra != null){
                        setDataCompra(dataValidaCompra);
                        break; // Sai do loop se a data for válida
                    }
                }

                 while(true){
                    System.out.print("Informe a nova data de vencimento (dd/MM/yyyy): ");
                    String novaDataVencimento = sc.nextLine();

                    String dataValidaVen = h.dataPadraoBanco(novaDataVencimento);
                    
                    if(dataValidaVen != null){
                        setDataVencimento(dataValidaVen);
                        break; // Sai do loop se a data for válida    
                    }
                }
                ed = lt.editarLote(getCodLote(), getDataCompra(), getDataVencimento(), getQntdGarrafa(), getTipoOleo());
                break;

            case 0:
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                return;
        }

        if (ed == false) {
            System.out.println("Erro ao editar o lote.");
        } else {
            System.out.println("Edição realizada com sucesso.");
        }
    }

    public void consLote() {
        lt.listarLote();
    }

    public void delLote() {
        boolean del = false;
        
        lt.listaEdicao();
        System.out.print("\nCódigo do lote que será apagado: ");
        String loteID = sc.nextLine();
        setCodLote(loteID);

        while (validarLote() == false) {
            System.out.println("Lote não encontrado. Tente novamente.");
            String loteCod = sc.nextLine();
            setCodLote(loteCod);  // Atualiza o loteID antes da próxima validação
        }

        del = lt.apagarLote(getCodLote());
        if (del == false) {
            System.out.println("Erro ao apagar o lote.");
        } else {
            System.out.println("Lote excluído com sucesso.");
        }
    }

    // -------------- MÉTODOS DE APOIO --------------
    public boolean validarLote() {
        if (lt.validaCOD(getCodLote()) == 1) {
            return true;
        } else {
            return false;
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
