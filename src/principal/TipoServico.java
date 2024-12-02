package principal;

import dao.TipoServicoDAO;
import include.Helper;
import java.util.Scanner;

public class TipoServico {
    Integer numeroValidado = null;
    Helper h = new Helper();
    Scanner sc = new Scanner(System.in, "utf8");
    TipoServicoDAO ts = new TipoServicoDAO();

    private int idServico;
    private String descricaoServico;

    public void addServico() {
        boolean cad = false;
        System.out.println("Cadastrando novo serviço");

        System.out.print("Serviço: ");
        setDescricaoServico(sc.nextLine());

        cad = ts.cadastrarTipoServico(getDescricaoServico());
        if (cad == false) {
            System.out.println("Algo deu errado");
        } else {
            System.out.println("Cadastro realizado com sucesso.");
        }
    }

    public void edServico() {
        numeroValidado = null;
        boolean ed = false;
        setIdServico(idServico);
        
        if (ts.listaEdicao()== 0) {
            System.out.println("\t\t\tNenhum dado encontrado");
            return;
        }

        while (numeroValidado == null) {
            System.out.print("Digite o id do serviço: ");
            String servicoID = sc.nextLine();
            setIdServico(idServico);

            // Valida se a entrada é numérica
            numeroValidado = h.isNumeric(servicoID);

            if (numeroValidado == null) {
                System.out.println("Apenas números!.");
            } else {
                // Verifica se o cliente existe no banco de dados
                setIdServico(numeroValidado);
                if (validarServico() == false) {
                    System.out.println("ID não encontrado. Tente novamente.");
                    numeroValidado = null;  // Redefine para continuar o loop
                }else{
                    System.out.print("\nDigite o novo nome do serviço: ");
                    setDescricaoServico(sc.nextLine());
                    
                    ed = ts.editarTipoServico(getIdServico(), getDescricaoServico());
                }
            }
        }
 
            
        
        if (!ed) {
            System.out.println("Erro ao editar o serviço.");
        } else {
            System.out.println("Edição realizada com sucesso.");
        }
    }

    public void consServico() {
        ts.listarTiposServico();
    }

    public void delServico() {
        boolean del = false;
        numeroValidado = null;
        
        if (ts.listaEdicao() == 0) {
            System.out.println("\t\t\tNenhum dado encontrado");
            return;
        }
        
        // Loop para validar a entrada numérica e a existência no banco
        while (numeroValidado == null || validarServico() == false) {
            System.out.print("Digite o ID do serviço: ");
            String servicoID = sc.nextLine();
            // Tenta validar se a entrada é numérica
            numeroValidado = h.isNumeric(servicoID);

            if (numeroValidado == null) {
                System.out.println("Apenas números!");
            } else {
                // com o o ID numérico, verifica no banco
                setIdServico(numeroValidado);
                if (!validarServico()) {
                    System.out.println("Serviço não encontrado. Tente novamente.");
                    numeroValidado = null;
                }
            }
        }

        del = ts.apagarTipoServico(getIdServico());
        if (del == false) {
            System.out.println("Erro ao apagar o serviço.");
        } else {
            System.out.println("Cadastro excluído.");
        }
    }

// -------------- MÉTODOS DE APOIO --------------
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
