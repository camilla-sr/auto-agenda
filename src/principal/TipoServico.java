package principal;

import dao.TipoServicoDAO;
import include.Helper;
import java.util.Scanner;

public class TipoServico {
    Integer numeroValidado = null;
    Helper h = new Helper();
    Scanner sc = new Scanner(System.in, "utf8");
    Scanner num = new Scanner(System.in);
    TipoServicoDAO ts = new TipoServicoDAO();

    private int idServico;
    private String descricaoServico;

    public void addServico() {
        boolean cad = false;

        System.out.println("Cadastrando novo servi�o");

        System.out.print("Servi�o: ");
        setDescricaoServico(sc.nextLine());

        cad = ts.cadastrarTipoServico(getDescricaoServico());
        if (cad == false) {
            System.out.println("Algo deu errado");
        } else {
            System.out.println("Cadastro realizado com sucesso.");
        }
    }

    public void edServico() {
        boolean ed = false;

        while (numeroValidado == null) {
            System.out.print("Digite o id do servi�o: ");
            String clienteID = sc.nextLine();

            // Valida se a entrada � num�rica
            numeroValidado = h.isNumeric(clienteID);

            if (numeroValidado == null) {
                System.out.println("ID inv�lido. Digite novamente.");
            } else {
                // Verifica se o cliente existe no banco de dados
                setIdServico(numeroValidado);
                if (validarServico() == false) {
                    System.out.println("ID n�o encontrado. Tente novamente.");
                    numeroValidado = null;  // Redefine para continuar o loop
                }
            }
        }
        
        if (ed == false) {
            System.out.println("Erro ao editar o cliente.");
        } else {
            System.out.println("Edi��o realizada com sucesso.");
        }
    }

    public void consServico() {
        ts.listarTiposServico();
    }

    public void delServico() {
        boolean del = false;
        String clienteID = "";

        // Loop para validar a entrada num�rica e a exist�ncia no banco
        while (numeroValidado == null || !validarServico()) {
            System.out.print("Digite o ID do cliente: ");
            clienteID = sc.nextLine();

            // Tenta validar se a entrada � num�rica
            numeroValidado = h.isNumeric(clienteID);

            if (numeroValidado == null) {
                System.out.println("ID inv�lido. Digite apenas n�meros.");
            } else {
                // com o o ID num�rico, verifica no banco
                setIdServico(numeroValidado);
                if (!validarServico()) {
                    System.out.println("Servi�o n�o encontrado. Tente novamente.");
                    numeroValidado = null;
                }
            }
        }

        del = ts.apagarTipoServico(getIdServico());
        if (del == false) {
            System.out.println("Erro ao apagar o servi�o.");
        } else {
            System.out.println("Cadastro exclu�do.");
        }
    }

// -------------- M�TODOS DE APOIO --------------
    public boolean validarServico() {
        if (ts.validaID(getIdServico())) {
            return true;
        } else {
            return false;
        }
    }

    // -------------- GETTERS E SETTERS --------------
    public int getIdServico() {
        return idServico;
    }
    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }
    public String getDescricaoServico() {
        return descricaoServico;
    }
    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }
    // -----------------------------------------------
}
